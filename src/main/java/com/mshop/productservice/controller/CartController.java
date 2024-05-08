package com.mshop.productservice.controller;

import com.mshop.productservice.client.UserClient;
import com.mshop.productservice.entity.Cart;
import com.mshop.productservice.service.CartDetailService;
import com.mshop.productservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CartDetailService cartDetailService;

    @Autowired
    UserClient userClient;

    @GetMapping("/user/{id}")
    public ResponseEntity<Cart> getCartUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.getCartUser(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Cart> putCartUser(@PathVariable("id") Long id, @RequestBody Cart cart) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.save(cart));
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<Cart> postCartUser(@PathVariable("id") Long id, @RequestBody Cart cart) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartService.save(cart));
    }

}
