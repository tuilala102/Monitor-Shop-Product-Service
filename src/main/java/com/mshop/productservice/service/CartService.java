package com.mshop.productservice.service;
import com.mshop.productservice.entity.Cart;

public interface CartService {
	public Cart getCartUser(Long id);
	
	public Cart save(Cart cart);
	
	public Boolean existsById(Long id);
}
