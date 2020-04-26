package kovteba.onlineshopapi.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.SwaggerDefinition;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.mapper.UserMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.GeneratePDF;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopcommon.enums.RoleUser;
import kovteba.onlineshopcommon.pojo.User;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.codehaus.commons.compiler.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

//    @Value("${online.out.storage.pdf}")
//    private String directoty;

    //    private final String directoty = "online-shop-api/src/main/resources/pdf/";//files/
    private final String directoty = "files/";//files/

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final GeneratePDF generatePDF;

    private final EmailService emailService;

    private final UserMapper userMapper;
    private final ResourceLoader resourceLoader;

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
        return ResponseEntity.status(responce.getStatus())
                .body(user);
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


    private static final String INTERNAL_FILE = "irregular-verbs-list.pdf";
    private static final String EXTERNAL_FILE_PATH = "files/user1gmail.comReceipt.zip";


    @GetMapping("/genRec/{userId}")
    public void  /*ResponseEntity<ByteArrayResource>*/ generateReceipt(@RequestHeader(value = "Authorization") String token,
                                                                       @PathVariable Long userId,
                                                                       HttpServletResponse response,
                                                                       HttpServletRequest request) throws IOException, DocumentException {

        UserEntity userEntity = (UserEntity) userService.getUserById(userId).getObject();
        String fileName = generatePDF.generateDPF(userEntity.getBasket(), userEntity.getEmail());

//        Path path = Paths.get(directoty + fileName);
//        byte[] data = Files.readAllBytes(path);
//        ByteArrayResource resource = new ByteArrayResource(data);
//        return ResponseEntity.ok()
//                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
//                // Content-Type
//                .contentType(MediaType.APPLICATION_PDF)
//                // Content-Lengh
//                .contentLength(data.length)
//                .body(resource);


        File file = new File(directoty + fileName);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());




//        byte[] contents = document.toString().getBytes();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("online-shop-api/src/main/resources/pdf/user1Receipt.pdf", "online-shop-api/src/main/resources/pdf/user1Receipt.pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping("/sendEmail")
    public void sendEmail(@RequestHeader(value = "Authorization") String token) {
        emailService.sendSimpleMessage("kovteba@gmail.com", "Test", "TEST TEXT");
    }

}
