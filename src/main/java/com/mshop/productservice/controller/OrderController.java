package com.mshop.productservice.controller;

import com.mshop.productservice.client.UserClient;
import com.mshop.productservice.dto.event.OrderEvent;
import com.mshop.productservice.entity.Order;
import com.mshop.productservice.service.OrderDetailService;
import com.mshop.productservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    UserClient userClient;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping()
    public ResponseEntity<List<Order>> getAll() {
        return ResponseEntity.ok(orderService.findAllOrderDesc());
    }

    @GetMapping("/wait")
    public ResponseEntity<List<Order>> getAllWait() {
        return ResponseEntity.ok(orderService.findAllOrderWait());
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOne(@PathVariable("id") Long id) {
        if (!orderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findById(id).get());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Order>> getAllByUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findAllOrderByUserId(id));
    }


    @GetMapping("/user/wait/{id}")
    public ResponseEntity<List<Order>> getAllWaitByUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findAllOrderWaitByUserId(id));
    }

    @GetMapping("/user/confirmed/{id}")
    public ResponseEntity<List<Order>> getAllConfirmedByUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findAllOrderConfirmedByUserId(id));
    }

    @GetMapping("/user/paid/{id}")
    public ResponseEntity<List<Order>> getAllPaidByUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findAllOrderPaidByUserId(id));
    }

    @GetMapping("/user/cancel/{id}")
    public ResponseEntity<List<Order>> getAllCancelByUser(@PathVariable("id") Long id) {
        if (!userClient.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.findAllOrderCancelByUserId(id));
    }

    @PostMapping
    public ResponseEntity<Order> post(@RequestBody Order order) {
        if (orderService.existsById(order.getId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!userClient.existsById(order.getUser().getUserId())) {
            return ResponseEntity.notFound().build();
        }
        order.setUserId(order.getUser().getUserId());
        Order o = orderService.save(order);
        publishOrderEvent(o.getId(), o.getUserId(), order.getStatus());
        return ResponseEntity.ok(o);
    }


    @PutMapping("{id}")
    public ResponseEntity<Order> put(@PathVariable("id") Long id, @RequestBody Order order) {
        if (!orderService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (id != order.getId()) {
            return ResponseEntity.badRequest().build();
        }
        Order o = orderService.save(order);
        publishOrderEvent(order.getId(), order.getUserId(), order.getStatus());
        return ResponseEntity.ok(o);
    }

    private void publishOrderEvent(Long orderId, Long userId, Integer status) {
        if (status == null) {
            log.error("[Order] Loi khi publish event, null status");
            return;
        }
        // 2 = xac nhan
        // 3 = da thanh toan
        // 0 = huy
        OrderEvent.Event event = null;
        switch (status) {
            case 0:
                event = OrderEvent.Event.ADMIN_CANCELED;
                break;
            case 1:
                event = OrderEvent.Event.USER_ORDER;
                break;
            case 2:
                event = OrderEvent.Event.ADMIN_ACCEPTED;
                break;
            case 3:
                event = OrderEvent.Event.ADMIN_PAID_CONFIRM;
                break;
        }
        if (event == null) {
            log.error("[Order] Loi khi publish event, null event, status = " + status);
            return;
        }

        applicationEventPublisher.publishEvent(new OrderEvent()
                .setOrderId(orderId)
                .setUserId(userId)
                .setEvent(event)
        );
    }

}
