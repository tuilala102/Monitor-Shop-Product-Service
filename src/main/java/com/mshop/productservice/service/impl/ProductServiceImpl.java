package com.mshop.productservice.service.impl;

import com.mshop.productservice.client.FileClient;
import com.mshop.productservice.entity.Product;
import com.mshop.productservice.repository.ProductRepository;
import com.mshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileClient fileClient;

    public List<Product> findAllProductByCategoryId(Long id) {
        return productRepository.findAllProductByCategoryId(id);
    }

    public List<Product> findAllStatus() {
        return productRepository.findAllStatusTrue();
    }

    public Product findByIdAndStatusTrue(Long id) {
        return productRepository.findByIdAndStatusTrue(id);
    }

    @Transactional
    public Product save(Product cart) {
        return productRepository.save(cart);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Product saveProductImage(Product product) {
        if (product.getImage() != null && product.getImage().startsWith("data:image")) {
            String key = fileClient.saveFileString(product.getImage());
            product.setImage(key);
        }
        return product;
    }

    public Product setProductImage(Product product) {
        if (product != null && product.getImage() != null && !product.getImage().startsWith("data:image")) {
            product.setImage(fileClient.getFileString(product.getImage()));
        }
        return product;
    }

    public List<Product> setProductImage(List<Product> products) {
        if (ObjectUtils.isEmpty(products)) {
            return List.of();
        }
        Map<String, Product> productAndImage = new HashMap<>();
        for (Product product : products) {
            if (product.getImage() != null && !product.getImage().startsWith("data:image")) {
                productAndImage.put(product.getImage(), product);
            }
        }
        if (!ObjectUtils.isEmpty(productAndImage)) {
            Map<String, String> imageMap = fileClient.getFileStrings(productAndImage.keySet());
            if (!ObjectUtils.isEmpty(imageMap)) {
                for (var entry : productAndImage.entrySet()) {
                    String imageBase64 = imageMap.get(entry.getKey());
                    entry.getValue().setImage(imageBase64);
                }
            }
        }
        return products;
    }

}
