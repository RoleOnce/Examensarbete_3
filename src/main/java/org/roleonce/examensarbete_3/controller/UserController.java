package org.roleonce.examensarbete_3.controller;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {

        model.addAttribute("customUser", new CustomUser());

        return "register";
    }

}
