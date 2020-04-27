package kovteba.onlineshopapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {


    @GetMapping
    public String getPrivate(@RequestHeader(value = "Authorization") String token){
        return "PRIVATE";
    }

}


