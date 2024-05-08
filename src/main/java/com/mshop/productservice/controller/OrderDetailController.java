package com.mshop.productservice.controller;

import com.mshop.productservice.entity.OrderDetail;
import com.mshop.productservice.entity.Product;
import com.mshop.productservice.service.OrderDetailService;
import com.mshop.productservice.service.OrderService;
import com.mshop.productservice.service.ProductService;
import one.util.streamex.StreamEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order-detail")
public class OrderDetailController {
    @Autowired
    OrderDetailService repo;

    @Autowired
    OrderService Orepo;

    @Autowired
    ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<OrderDetail> get(@PathVariable("id") Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        OrderDetail orderDetail = repo.findById(id).get();
        productService.setProductImage(orderDetail.getProduct());
        return ResponseEntity.ok(orderDetail);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDetail> put(@PathVariable("id") Long id, @RequestBody OrderDetail orderDetail) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (id != orderDetail.getId()) {
            return ResponseEntity.badRequest().build();
        }
        OrderDetail orderDetail1 = repo.save(orderDetail);
        productService.setProductImage(orderDetail.getProduct());
        return ResponseEntity.ok(orderDetail1);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailByOrder(@PathVariable("id") Long id) {
        if (!Orepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<OrderDetail> orderDetails = repo.findOrderDetailByOrderId(id);
        List<Product> products = StreamEx.of(orderDetails).map(OrderDetail::getProduct).toList();
        productService.setProductImage(products);
        return ResponseEntity.ok(orderDetails);
    }

    @PostMapping()
    public ResponseEntity<OrderDetail> post(@RequestBody OrderDetail orderDetail) {
        if (repo.existsById(orderDetail.getId())) {
            return ResponseEntity.badRequest().build();
        }
        OrderDetail orderDetail1 = repo.save(orderDetail);
        productService.saveProductImage(orderDetail1.getProduct());
        return ResponseEntity.ok(repo.save(orderDetail));
    }

}
