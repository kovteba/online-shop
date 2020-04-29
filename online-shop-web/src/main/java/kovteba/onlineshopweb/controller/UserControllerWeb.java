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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/users")
public class UserControllerWeb {

    private final Logger log = LoggerFactory.getLogger(UserControllerWeb.class);

    private final UserServiceWeb userServiceWeb;

    public UserControllerWeb(UserServiceWeb userServiceWeb) {
        this.userServiceWeb = userServiceWeb;
    }

    @PostMapping(value = "/login")
    public String logIn(@RequestBody JwtRequest authenticationRequest, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("TTTTTTTTTTTEST : " + authenticationRequest.getEmail() + " " + authenticationRequest.getPassword());
        String token = userServiceWeb.auth(authenticationRequest);

//        redirectAttributes.addAttribute("token", token);
        request.getSession().setAttribute("token", token);
        System.out.println("QQQQQQQQQQQQQQQ AUTH " + token);

        return "redirect:/users/userBasket";
    }

    @GetMapping("/userBasket")
    public String userBasket(Model model, HttpServletRequest request) throws IOException {
        String token = (String) request.getSession().getAttribute("token");
        System.out.println("QQQQQQQQQQQQQQQ user basket " + token);
        User user = userServiceWeb.getUserByEmail(token);
        System.out.println(user.toString());

        return "product_summary";
    }

}
