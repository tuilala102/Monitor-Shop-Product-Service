package com.mshop.productservice.service;

import com.mshop.productservice.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
	public List<OrderDetail> findOrderDetailByOrderId(Long id);
	
	public OrderDetail save(OrderDetail detail);
	
	public Optional<OrderDetail> findById(Long id);
	
	public Boolean existsById(Long id);
}
