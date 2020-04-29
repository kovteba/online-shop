package kovteba.onlineshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.mapper.UserMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.BanService;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.GeneratePDF;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopcommon.enums.RoleUser;
import kovteba.onlineshopcommon.pojo.User;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${online.out.storage.pdf}")
    private String directory;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final GeneratePDF generatePDF;
    private final UserMapper userMapper;
    private final BanService banService;

    public UserController(UserService userService,
                          JwtTokenUtil jwtTokenUtil,
                          GeneratePDF generatePDF,
                          EmailService emailService,
                          UserMapper userMapper,
                          BanService banService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.generatePDF = generatePDF;
        this.userMapper = userMapper;
        this.banService = banService;
    }

    @GetMapping("/role")
    public ResponseEntity<List<User>> getUserByRole(@RequestHeader(value = "Authorization") String token,
                                                    @RequestHeader String roleUser) {
        log.info("getUserByRole, " + this.getClass());
        Responce responce = userService.getUserByRole(RoleUser.findRole(roleUser));
        List<User> users = ((List<UserEntity>) responce.getObject())
                .stream()
                .map(userMapper::userEntityToUser).collect(Collectors.toList());
        return ResponseEntity.status(responce.getStatus())
                .body(users);
    }

    @PostMapping("/phone")
    public ResponseEntity<User> getUserByPhoneNumber(@RequestHeader(value = "Authorization") String token,
                                                     @RequestBody String phoneNumber) {
        log.info("getUserByPhoneNumber, " + this.getClass());
        Responce responce = userService.getUserByPhoneNumber(phoneNumber);
        System.out.println(responce.getObject());
        return ResponseEntity.status(responce.getStatus())
                .body(userMapper.userEntityToUser((UserEntity) responce.getObject()));
    }

    @PostMapping("/basket/{userId}/{productId}")
    public ResponseEntity<User> addToBasket(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable Long userId,
                                            @PathVariable Long productId,
                                            @RequestBody String count) {
        log.info("addToBasket, " + this.getClass());
        Responce responce = userService.addToBasketUser(userId, productId, count);
        return ResponseEntity.status(responce.getStatus())
                .body(userMapper.userEntityToUser((UserEntity) responce.getObject()));
    }

    @GetMapping(
            value = "/genRec/{userId}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateReceipt(@RequestHeader(value = "Authorization") String token,
                                @PathVariable Long userId,
                                HttpServletResponse response) throws IOException {
        String email = jwtTokenUtil.getEmailFromToken(token);
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getObject();
        if (userEntity != null && email.equals(userEntity.getEmail())) {
            String fileName = generatePDF.generateDPF(userEntity.getBasket(), userEntity.getEmail());
            File file = new File(directory + fileName);
            InputStream targetStream = new DataInputStream(new FileInputStream(file));
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            IOUtils.copy(targetStream, response.getOutputStream());
        }
    }

    @GetMapping("/ban/{email}")
    public ResponseEntity<?> banUserByEmail(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String email) {
        Responce responce = banService.banUserByEmail(email);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @GetMapping("/unBan/{email}")
    public ResponseEntity<?> unBanUserByEmail(@RequestHeader(value = "Authorization") String token,
                                              @PathVariable String email) {
        Responce responce = banService.unBanUserByEmail(email);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @GetMapping("/email")
    public String getUserByEmail(@RequestHeader(value = "Authorization") String token) throws IOException {
        log.info("getUserByEmail, " + this.getClass());
        String email = jwtTokenUtil.getEmailFromToken(token);
        Responce responce = userService.getUserByEmail(email);
        User user = userMapper.userEntityToUser((UserEntity) responce.getObject());
//        return ResponseEntity.status(responce.getStatus()).body(user);

        StringWriter writer = new StringWriter();
        //это объект Jackson, который выполняет сериализацию
        ObjectMapper mapper = new ObjectMapper();
        // сама сериализация: 1-куда, 2-что
        mapper.writeValue(writer, user);
        //преобразовываем все записанное во StringWriter в строку
        String result = writer.toString();
        return result;

    }

}
