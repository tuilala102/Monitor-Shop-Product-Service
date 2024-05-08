package com.mshop.productservice.service.impl;

import com.mshop.productservice.entity.CartDetail;
import com.mshop.productservice.repository.CartDetailRepository;
import com.mshop.productservice.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    public List<CartDetail> getByCartId(Long id) {
        return cartDetailRepository.getByCartId(id);
    }

    public Long getCount(Long id) {
        return cartDetailRepository.getCount(id);
    }

    @Transactional
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    public Boolean existsById(Long id) {
        return cartDetailRepository.existsById(id);
    }

    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        cartDetailRepository.deleteById(id);
        ;
    }
}
