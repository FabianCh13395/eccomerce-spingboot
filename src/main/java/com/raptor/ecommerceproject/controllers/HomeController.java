package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.Order;
import com.raptor.ecommerceproject.models.OrderDetail;
import com.raptor.ecommerceproject.models.Product;
import com.raptor.ecommerceproject.services.ProductService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    //Almacenar los detalles de la orden
    List<OrderDetail> details=new ArrayList<OrderDetail>();

    //Datos de la orden
    Order order=new Order();

    @GetMapping("")
    public String home(Model model){
        List<Product> productList=productService.findAll();
        model.addAttribute("products",productList);
        return "User/home";
    }

    @GetMapping("productohome/{id}")
    public String productHome(@PathVariable Long id,Model model){
        log.info("Id enviado como parámetro {}",id);
        Product product=new Product();
        Optional<Product> productOptional=productService.get(id);
        product=productOptional.get();
        model.addAttribute("product",product);
        return "User/productohome";
    }

     @PostMapping("/cart")
    public String addCart(@RequestParam Long id,@RequestParam Integer cantidad,Model model){
        OrderDetail orderDetail=new OrderDetail();
        Product product=new Product();
        double sumTotal=0;

        Optional<Product> optionalProduct=productService.get(id);
        log.info("EL producto recuperado es: {}",optionalProduct.get());
        log.info("Cantidad: {}",cantidad);
        product=optionalProduct.get();
        orderDetail.setQuantityOrder(cantidad.doubleValue());
        orderDetail.setPriceOrder(product.getPrice());
        orderDetail.setNameOrder(product.getNameProduct());
        orderDetail.setTotalOrder(product.getPrice()*cantidad);
        orderDetail.setProduct(product);

        //validar que el producto no se añada dos veces
         Long idProduct=product.getId();
         boolean entered=details.stream().anyMatch(p-> p.getProduct().getId()==idProduct);
         if (!entered){
             details.add(orderDetail);
         }

        sumTotal=details.stream().mapToDouble(dt->dt.getTotalOrder()).sum();
        order.setTotal(sumTotal);
        model.addAttribute("cart",details);
        model.addAttribute("orden",order);
        return "User/carrito";
     }

     // Quitar un producto del carrito de compras
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCar(@PathVariable Long id,Model model){
        //Lista nueva de productos
        List<OrderDetail>orderNew=new ArrayList<OrderDetail>();
        for (OrderDetail orderDetail:details){
            if(orderDetail.getProduct().getId()!=id){
                orderNew.add(orderDetail);
            }
        }
        //Nueva lista con los productos que nno se han quitado
        details=orderNew;
        double sumTotal=0;
        sumTotal=details.stream().mapToDouble(dt->dt.getTotalOrder()).sum();
        order.setTotal(sumTotal);
        model.addAttribute("cart",details);
        model.addAttribute("orden",order);

        return "User/carrito";
    }
    @GetMapping("/getCart")
    public String getCart(Model model){

        model.addAttribute("cart",details);
        model.addAttribute("orden",order);
        return "/User/carrito";
    }



}
