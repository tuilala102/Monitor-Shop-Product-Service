package com.mshop.productservice.service.impl;

import com.mshop.productservice.client.UserClient;
import com.mshop.productservice.dto.User;
import com.mshop.productservice.entity.Order;
import com.mshop.productservice.repository.OrderRepository;
import com.mshop.productservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserClient userClient;
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAllOrderDesc() {

        List<Order> orders = orderRepository.findAllOrderDesc();
        if (orders == null || orders.isEmpty()) {
            return orders;
        }

        List<Long> userIds = new ArrayList<>();
        for (Order order : orders) {
            userIds.add(order.getUserId());
        }
        List<User> users = userClient.getAllByIds(userIds);
        Map<Long, User> usersMap = new HashMap<>();
        for (User user : users) {
            usersMap.put(user.getUserId(), user);
        }
        for (Order order : orders) {
            if (order.getUserId() != null) {
                order.setUser(usersMap.get(order.getUserId()));
            }
        }
        return orders;
    }

    public List<Order> findAllOrderByUserId(Long id) {
        return orderRepository.findAllOrderByUserId(id);
    }

    public List<Order> findAllOrderWait() {
        return orderRepository.findAllOrderWait();
    }

    public List<Order> findAllOrderCancelByUserId(Long id) {
        return orderRepository.findAllOrderCancelByUserId(id);
    }

    public List<Order> findAllOrderWaitByUserId(Long id) {
        return orderRepository.findAllOrderWaitByUserId(id);
    }

    public List<Order> findAllOrderConfirmedByUserId(Long id) {
        return orderRepository.findAllOrderConfirmedByUserId(id);
    }

    public List<Order> findAllOrderPaidByUserId(Long id) {
        return orderRepository.findAllOrderPaidByUserId(id);
    }

    public List<Object[]> getStatisticalMonthYear(int year) {
        return orderRepository.getStatisticalMonthYear(year);
    }

    public List<Object[]> getStatisticalYear() {
        return orderRepository.getStatisticalYear();
    }

    public List<Object[]> getStatisticalMonth() {
        return orderRepository.getStatisticalMonth();
    }

    public List<Object[]> getStatisticalDate() {
        return orderRepository.getStatisticalDate();
    }

    public List<Integer> getYears() {
        return orderRepository.getYears();
    }

    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
