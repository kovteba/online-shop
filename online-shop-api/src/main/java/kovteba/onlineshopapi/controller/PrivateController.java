package kovteba.onlineshopapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    public String getPrivate(@RequestHeader(value = "Authorization") String token){
        File file = new File("online-shop-common/files//photo5276245698708352805.jpg");

        return file.getName();
    }

}


