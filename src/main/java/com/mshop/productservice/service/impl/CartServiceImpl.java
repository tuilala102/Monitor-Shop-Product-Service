package com.mshop.productservice.service.impl;

import com.mshop.productservice.entity.Cart;
import com.mshop.productservice.repository.CartRepository;
import com.mshop.productservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
    private CartRepository cartRepository;
	
	public Cart getCartUser(Long id) {
        return cartRepository.getCartUser(id);
    }
	
	@Transactional
	public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
	
	public Boolean existsById(Long id) {
		return cartRepository.existsById(id);
	}
}
