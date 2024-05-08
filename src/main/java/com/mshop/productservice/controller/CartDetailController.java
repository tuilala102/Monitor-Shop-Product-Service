package com.mshop.productservice.controller;

import com.mshop.productservice.entity.CartDetail;
import com.mshop.productservice.entity.Product;
import com.mshop.productservice.service.CartDetailService;
import com.mshop.productservice.service.CartService;
import com.mshop.productservice.service.ProductService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-detail")
public class CartDetailController {
    @Autowired
    CartDetailService cartDetailService;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;


    @GetMapping("cart/{id}")
    public ResponseEntity<List<CartDetail>> getByCartId(@PathVariable("id") Long id) {
        if (!cartService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<CartDetail> cartDetails = cartDetailService.getByCartId(id);
        List<Product> products = StreamEx.of(cartDetails).map(CartDetail::getProduct).toList();
        productService.setProductImage(products);
        return ResponseEntity.ok(cartDetails);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<CartDetail> getOne(@PathVariable("id") Long id) {
        if (!cartDetailService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        CartDetail cartDetail = cartDetailService.findById(id).get();
        productService.setProductImage(cartDetail.getProduct());
        return ResponseEntity.ok(cartDetail);
    }

    @PostMapping()
    public ResponseEntity<CartDetail> post(@RequestBody CartDetail detail) {
        if (!cartService.existsById(detail.getCart().getId())) {
            return ResponseEntity.notFound().build();
        }
        boolean check = false;
        List<Product> listP = productService.findAllStatus();
        Product product = productService.findByIdAndStatusTrue(detail.getProduct().getProductId());
        for (Product p : listP) {
            if (p.getProductId() == product.getProductId()) {
                check = true;
            }
        }
        if (!check) {
            return ResponseEntity.notFound().build();
        }
        List<CartDetail> listD = cartDetailService.getByCartId(detail.getCart().getId());
        for (CartDetail item : listD) {
            if (item.getProduct().getProductId() == detail.getProduct().getProductId()) {
                item.setQuantity(item.getQuantity() + 1);
                item.setPrice(item.getPrice() + detail.getPrice());
                productService.setProductImage(item.getProduct());
                return ResponseEntity.ok(cartDetailService.save(item));
            }
        }
        productService.setProductImage(detail.getProduct());
        return ResponseEntity.ok(cartDetailService.save(detail));
    }

    @PutMapping()
    public ResponseEntity<CartDetail> put(@RequestBody CartDetail detail) {
        if (!cartService.existsById(detail.getCart().getId())) {
            return ResponseEntity.notFound().build();
        }
        CartDetail cartDetail = cartDetailService.save(detail);
        productService.setProductImage(cartDetail.getProduct());
        return ResponseEntity.ok(cartDetail);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!cartDetailService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartDetailService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
