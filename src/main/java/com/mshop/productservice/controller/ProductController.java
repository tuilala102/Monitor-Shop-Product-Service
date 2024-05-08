package com.mshop.productservice.controller;

import com.mshop.productservice.dto.event.ProductEvent;
import com.mshop.productservice.entity.Product;
import com.mshop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.setProductImage(productService.findAllStatus());
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") Long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<Product> list = productService.findAllStatus();
        Product product = productService.findByIdAndStatusTrue(id);
        boolean check = false;
        for (Product p : list) {
            if (p.getProductId() == product.getProductId()) {
                check = true;
            }
        }
        ;
        if (!check) {
            return ResponseEntity.notFound().build();
        }
        productService.setProductImage(product);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product product) {
        if (productService.existsById(product.getProductId())) {
            return ResponseEntity.badRequest().build();
        }
        productService.saveProductImage(product);
        publishProductEvent(product.getProductId(), product.getName());
        return ResponseEntity.ok(productService.save(product));
    }

    public void publishProductEvent(Long id, String name) {
        applicationEventPublisher.publishEvent(new ProductEvent()
                .setProductId(id)
                .setProductName(name)
                .setEvent(ProductEvent.Event.CREATED)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> put(@PathVariable("id") Long id, @RequestBody Product product) {
        if (!id.equals(product.getProductId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.saveProductImage(product);
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Product pro = productService.findById(id).get();
        pro.setStatus(false);
        productService.save(pro);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-category/{id}")
    public ResponseEntity<List<Product>> getAllByCategory(@PathVariable("id") Long id) {
//		System.out.println(repo.findAllProductByCategoryId(id));
        List<Product> products = productService.findAllProductByCategoryId(id);
        productService.setProductImage(products);
        return ResponseEntity.ok(products);
    }


}
