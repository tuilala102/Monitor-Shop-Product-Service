package com.mshop.productservice.service;

import com.mshop.productservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAllProductByCategoryId(Long id);

    public List<Product> findAllStatus();

    public Product findByIdAndStatusTrue(Long id);

    public Product save(Product cart);

    public Optional<Product> findById(Long id);

    public Boolean existsById(Long id);

    Product saveProductImage(Product product);

    Product setProductImage(Product product);

    List<Product> setProductImage(List<Product> products);

}
