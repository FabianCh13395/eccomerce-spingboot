package com.raptor.ecommerceproject.services;

import com.raptor.ecommerceproject.models.Product;

import java.util.Optional;

public interface ProductService {
    //Metodo para implementar y guardar
    public Product save(Product product);
    //metodo para implementar y buscar un producto si es que existe o no en la base de datos.
    public Optional<Product> get(Long id);
    //Metodo para implementar y actualizar un producto
    public void update(Product product);
    //Metodo a implementar y eliminar un producto
    public void delete(Long id);
}
