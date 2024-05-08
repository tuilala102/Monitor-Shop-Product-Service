package com.mshop.productservice.service.impl;

import com.mshop.productservice.client.UserClient;
import com.mshop.productservice.dto.User;
import com.mshop.productservice.entity.Order;
import com.mshop.productservice.entity.Rate;
import com.mshop.productservice.repository.RateRepository;
import com.mshop.productservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    UserClient userClient;
    public List<Rate> findByProduct(Long id) {
        return rateRepository.findByProduct(id);
    }

    public Optional<Rate> findById(Long id) {
        return rateRepository.findById(id);
    }

    public Boolean existsById(Long id) {
        return rateRepository.existsById(id);
    }

    public Double getAvgByProduct(Long id) {
        return rateRepository.getAvgByProduct(id);
    }

    @Transactional
    public Rate save(Rate cart) {
        return rateRepository.save(cart);
    }

    public List<Rate> findAll() {
        List<Rate> rates = rateRepository.findAll();

        List<Long> userIds = new ArrayList<>();
        for (Rate rate : rates) {
            userIds.add(rate.getUserId());
        }
        List<User> users = userClient.getAllByIds(userIds);
        Map<Long, User> usersMap = new HashMap<>();
        for (User user : users) {
            usersMap.put(user.getUserId(), user);
        }
        for (Rate rate : rates) {
            if (rate.getUserId() != null) {
                rate.setUser(usersMap.get(rate.getUserId()));
            }
        }
        return rates;
    }

    public void deleteById(Long id) {
        rateRepository.deleteById(id);
    }
}
