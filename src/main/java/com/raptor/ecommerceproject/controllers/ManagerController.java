package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.Product;
import com.raptor.ecommerceproject.services.IOrderService;
import com.raptor.ecommerceproject.services.ProductService;
import com.raptor.ecommerceproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("")
    public String home(Model model){
        List<Product> products=productService.findAll();
        model.addAttribute("products",products);
        return "manager/home";
    }
    //Ver todos los usuarios
    @GetMapping("/users")
    public String userAll(Model model){
            model.addAttribute("users",userService.findAll());
        return "manager/usuarios";
    }

    @GetMapping("/orders")
    public String ordersAll(Model model){
        model.addAttribute("orders",iOrderService.findAll());
        return "manager/ordenes";
    }
}
