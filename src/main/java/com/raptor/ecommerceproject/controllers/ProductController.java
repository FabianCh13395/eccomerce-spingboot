package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.Product;
import com.raptor.ecommerceproject.models.User;
import com.raptor.ecommerceproject.services.ProductService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/products")
public class ProductController {
    private final Logger LOGGER= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String homeProduct(Model model){
        model.addAttribute("productos",productService.findAll());
        return "products/show";
    }

    @GetMapping("/create")
    public String viewCreate(){
        return "products/create";
    }

    @PostMapping("/save")
    public String saveProduct(Product product){
        LOGGER.info("Este es el objeto producto {}",product);
        User user=new User(1L,"","","","","","","");
        product.setUser(user);
        productService.save(product);
        return "redirect:/products";
    }


}
