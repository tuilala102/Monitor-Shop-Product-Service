package com.mshop.productservice.controller;

import com.mshop.productservice.entity.Product;
import com.mshop.productservice.entity.Rate;
import com.mshop.productservice.service.ProductService;
import com.mshop.productservice.service.RateService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("api/rates")
public class RateController {
    @Autowired
    RateService rateService;

    @Autowired
    ProductService productService;

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<List<Rate>> getAll() {
        List<Rate> rates = rateService.findAll();
        List<Product> products = StreamEx.of(rates).map(Rate::getProduct).toList();
        productService.setProductImage(products);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("{id}")
    public ResponseEntity<Rate> getOne(@PathVariable("id") Long id) {
        if (!rateService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Rate rate = rateService.findById(id).get();
        productService.setProductImage(rate.getProduct());
        return ResponseEntity.ok(rate);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<List<Rate>> getByProduct(@PathVariable("id") Long id) {
        List<Rate> rates = rateService.findByProduct(id);
        List<Product> products = StreamEx.of(rates).map(Rate::getProduct).toList();
        productService.setProductImage(products);
        return ResponseEntity.ok(rates);
    }

    @GetMapping("product-avg/{id}")
    public ResponseEntity<Double> getAvgProduct(@PathVariable("id") Long id) {
        Double rate = rateService.getAvgByProduct(id);
        if (rate == null) {
            rate = 0.0;
            return ResponseEntity.ok(rate);
        }
        return ResponseEntity.ok(rate);
    }

    @PostMapping()
    public ResponseEntity<Rate> post(@RequestBody Rate rate) {
        productService.setProductImage(rate.getProduct());
        return ResponseEntity.ok(rateService.save(rate));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        if (!rateService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rateService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
