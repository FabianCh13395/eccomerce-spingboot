package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.User;
import com.raptor.ecommerceproject.services.UserService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String createView(){
        return"User/registro";
    }

    @PostMapping("/save")
    public String saveUser(User user){
        user.setTypeUser("USER");
        logger.info("Datos del usuario registrado: {}",user);
        userService.save(user);

        return "redirect:/";
    }
}
