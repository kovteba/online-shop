package kovteba.onlineshopweb.controller;

import kovteba.onlineshopweb.service.UserServiceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    private final UserServiceWeb userServiceWeb;

    public MainController(UserServiceWeb userServiceWeb) {
        this.userServiceWeb = userServiceWeb;
    }

    @GetMapping("/")
    public String startPage(HttpServletRequest req, Model model, String email) {
//        System.out.println("@@@@@@@ : " + email);
        log.info("startPage " + this.getClass());

        return "index";
    }

}
