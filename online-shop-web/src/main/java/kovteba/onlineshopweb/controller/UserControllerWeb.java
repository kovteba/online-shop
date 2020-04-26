package kovteba.onlineshopweb.controller;


import kovteba.onlineshopcommon.pojo.User;
import kovteba.onlineshopcommon.model.*;

import kovteba.onlineshopweb.service.UserServiceWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/users")
public class UserControllerWeb {

    private final Logger log = LoggerFactory.getLogger(UserControllerWeb.class);

    private final UserServiceWeb userServiceWeb;

    public UserControllerWeb(UserServiceWeb userServiceWeb) {
        this.userServiceWeb = userServiceWeb;
    }

    @PostMapping(value = "/login")
    public String logIn(String email, String password, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        JwtRequest authenticationRequest = new JwtRequest();
        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);
        String token = userServiceWeb.auth(authenticationRequest);

        redirectAttributes.addAttribute("token", token);

        return "redirect:/users/userBasket";
    }

    @GetMapping("/userBasket")
    public String userBasket(Model model, String token) {
        User user = userServiceWeb.getUserByEmail(token);
        System.out.println(user.toString());

        return "product_summary";
    }

}
