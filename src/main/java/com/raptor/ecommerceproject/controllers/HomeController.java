package com.raptor.ecommerceproject.controllers;

import com.raptor.ecommerceproject.models.Order;
import com.raptor.ecommerceproject.models.OrderDetail;
import com.raptor.ecommerceproject.models.Product;
import com.raptor.ecommerceproject.models.User;
import com.raptor.ecommerceproject.services.IOrderDetailService;
import com.raptor.ecommerceproject.services.IOrderService;
import com.raptor.ecommerceproject.services.ProductService;
import com.raptor.ecommerceproject.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

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
    //Ir a la vista carrito con los productos añadidos
    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart",details);
        model.addAttribute("orden",order);
        return "/User/carrito";
    }

    @GetMapping("/order")
    public String orderUser(Model model){
        User user=userService.findById(1L).get();
        model.addAttribute("usuario",user);
        model.addAttribute("cart",details);
        model.addAttribute("orden",order);
        return "User/resumenorden";
    }

    //Metodo para guardar la Orden
    @GetMapping("/saveOrder")
    public String saveOrder(Model model){
        Date dateCreated=new Date();
        order.setDateCreation(dateCreated);
        order.setNumberOrder(orderService.generateNumberOrder());

        //Usuario
        User user=userService.findById(1L).get();
        order.setUserOrder(user);
        orderService.save(order);

        //guardarDetalles
        for (OrderDetail dt:details){
            dt.setOrder(order);
            orderDetailService.save(dt);
        }
        //limpiar lista y orden
        order=new Order();
        details.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre,Model model){
        log.info("Nombre del Producto: {}",nombre);
        List<Product> productList=productService.findAll().stream().filter(p->p.getNameProduct().toLowerCase().contains(nombre.toLowerCase())).collect(Collectors.toList());
        model.addAttribute("products",productList);
        return"User/home";
    }





}
