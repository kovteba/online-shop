package kovteba.onlineshopapi.controller;

import com.itextpdf.text.*;
import kovteba.onlineshopapi.entity.User;
import kovteba.onlineshopapi.enums.RoleUser;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.EmailService;
import kovteba.onlineshopapi.service.UserService;
import kovteba.onlineshopapi.util.GeneratePDF;
import kovteba.onlineshopapi.util.JwtTokenUtil;
import kovteba.onlineshopapi.util.ResponceType;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

//    @Value("${online.out.storage.pdf}")
//    private final String directoty;

    private final String directoty = "online-shop-api/src/main/resources/pdf/";

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final GeneratePDF generatePDF;

    private final EmailService emailService;

//    private final ServletContext servletContext;

    @GetMapping("/role")
    public ResponseEntity<ResponceType> getUserByRole(@RequestHeader(value = "Authorization") String token,
                                                      @RequestBody String roleUser) {
        log.info("getUserByRole, " + this.getClass());
        Responce responce = userService.getUserByRole(RoleUser.findRole(roleUser));
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @PostMapping("/phone")
    public ResponseEntity<ResponceType> getUserByPhoneNumber(@RequestHeader(value = "Authorization") String token,
                                                             @RequestBody String phoneNumber) {
        log.info("getUserByPhoneNumber, " + this.getClass());
        Responce responce = userService.getUserByPhoneNumber(phoneNumber);
        System.out.println(responce.getObject());
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @GetMapping("/email")
    public ResponseEntity<ResponceType> getUserByEmail(@RequestHeader(value = "Authorization") String token) {
        log.info("getUserByEmail, " + this.getClass());
        String email = jwtTokenUtil.getEmailFromToken(token);
        Responce responce = userService.getUserByEmail(email);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @PostMapping("/basket/{userId}/{productId}")
    public ResponseEntity<ResponceType> addToBasket(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable Long userId,
                                                    @PathVariable Long productId,
                                                    @RequestBody String count) {
        log.info("addToBasket, " + this.getClass());
        Responce responce = userService.addToBasketUser(userId, productId, count);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @GetMapping("/genRec/{userId}")
    public ResponseEntity<ByteArrayResource> generateReceipt(@RequestHeader(value = "Authorization") String token,
                                                             @PathVariable Long userId) throws IOException, DocumentException {
        User user = (User) userService.getUserById(userId).getObject();
        String fileName = generatePDF.generateDPF(user.getBasket(), user.getEmail());

        Path path = Paths.get(directoty + fileName);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(MediaType.APPLICATION_PDF)
                // Content-Lengh
                .contentLength(data.length)
                .body(resource);

//        byte[] contents = document.toString().getBytes();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("online-shop-api/src/main/resources/pdf/user1Receipt.pdf", "online-shop-api/src/main/resources/pdf/user1Receipt.pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    @GetMapping("/sendEmail")
    public void sendEmail(@RequestHeader(value = "Authorization") String token){
        emailService.sendSimpleMessage("kovteba@gmail.com", "Test", "TEST TEXT");
    }

}
