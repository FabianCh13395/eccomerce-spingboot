package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.Product;
import com.raptor.ecommerceproject.models.User;
import com.raptor.ecommerceproject.services.ProductService;
import com.raptor.ecommerceproject.services.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;


@Controller
@RequestMapping("/products")
public class ProductController {
    private final Logger LOGGER= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UploadFileService uploadFileService;

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
    public String saveProduct(Product product,@RequestParam("image") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto producto {}",product);
        User user=new User(1L,"","","","","","","");
        product.setUser(user);
        //Guardar Imagen
        if(product.getId()==null){ //Cuando se crea un producto
            String nameImage=uploadFileService.saveImage(file);
            product.setImage(nameImage);

        }else{
            if(file.isEmpty()){//Cuando se edita el producto, pero no se cambia la imagen
                Product p=new Product();
                p=productService.get(product.getId()).get();
                product.setImage(p.getImage());
            }else{
                String nameImage=uploadFileService.saveImage(file);
                product.setImage(nameImage);
            }
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,Model model){
        Product product=new Product();
        Optional <Product> optionalProduct=productService.get(id);
        product=optionalProduct.get();
        //LOGGER.info("Producto Buscado {}",product);
        model.addAttribute("producto",product);
        return "products/edit";
    }

    @PostMapping("/update")
    public String updateProduct(Product product){
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }


}
