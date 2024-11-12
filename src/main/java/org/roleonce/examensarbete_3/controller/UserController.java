package org.roleonce.examensarbete_3.controller;

import jakarta.validation.Valid;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {

        model.addAttribute("customUser", new CustomUser());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute(name = "customUser") CustomUser customUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userRepository.save(
                new CustomUser(
                        customUser.getUsername(),
                        bCryptPasswordEncoder.encode(customUser.getPassword()),
                        customUser.getEmail())
        );

        return "redirect:/";
    }

}
