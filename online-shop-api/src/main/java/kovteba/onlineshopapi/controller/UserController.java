package kovteba.onlineshopapi.controller;

import com.itextpdf.text.*;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.mapper.UserMapper;
import kovteba.onlineshopapi.responce.Responce;
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
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${online.out.storage.pdf}")
    private String directory;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final GeneratePDF generatePDF;

    private final EmailService emailService;

    private final UserMapper userMapper;

    public UserController(UserService userService,
                          JwtTokenUtil jwtTokenUtil,
                          GeneratePDF generatePDF,
                          EmailService emailService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.generatePDF = generatePDF;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    @GetMapping("/role")
    public ResponseEntity<User> getUserByRole(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody String roleUser) {
        log.info("getUserByRole, " + this.getClass());
        Responce responce = userService.getUserByRole(RoleUser.findRole(roleUser));
        return ResponseEntity.status(responce.getStatus())
                .body(userMapper.userEntityToUser((UserEntity) responce.getObject()));
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

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestHeader(value = "Authorization") String token) {
        log.info("getUserByEmail, " + this.getClass());
        String email = jwtTokenUtil.getEmailFromToken(token);
        Responce responce = userService.getUserByEmail(email);
        User user = userMapper.userEntityToUser((UserEntity) responce.getObject());
        return ResponseEntity.status(responce.getStatus()).body(user);
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

    @GetMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestHeader(value = "Authorization") String token) {
        emailService.sendSimpleMessage("kovteba@gmail.com", "Test", "TEST TEXT");
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATE SECRET TOKEN");
    }

    @GetMapping(
            value = "/genRec/{userId}",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateReceipt(@RequestHeader(value = "Authorization") String token,
                                @PathVariable Long userId,
                                HttpServletResponse response) throws IOException {
        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getObject();
        String fileName = generatePDF.generateDPF(userEntity.getBasket(), userEntity.getEmail());
        File file = new File(directory + fileName);
        InputStream targetStream = new DataInputStream(new FileInputStream(file));
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        IOUtils.copy(targetStream, response.getOutputStream());
    }


//    //////////////////////////////////////////////////////////////////////////
    @GetMapping(
            value = "/pic",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPic(HttpServletResponse response) throws IOException, DocumentException {
        File file = new File(directory + "IMG_1050.JPEG");
        InputStream targetStream = new DataInputStream(new FileInputStream(file));
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(targetStream, response.getOutputStream());
    }

    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> downloadByClick() throws IOException {
        UserEntity userEntity = (UserEntity) userService.getUserById(1L).getObject();
        String fileName = generatePDF.generateDPF(userEntity.getBasket(), userEntity.getEmail());
        Path path = Paths.get(directory + fileName);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(data.length)
                .body(resource);
    }

    @GetMapping(
            value = "/show",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public void showByClick(HttpServletResponse response) throws IOException, DocumentException {
        UserEntity userEntity = (UserEntity) userService.getUserById(1L).getObject();
        String fileName = generatePDF.generateDPF(userEntity.getBasket(), userEntity.getEmail());
        File file = new File(directory + fileName);
        InputStream targetStream = new DataInputStream(new FileInputStream(file));
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        IOUtils.copy(targetStream, response.getOutputStream());
    }

}
