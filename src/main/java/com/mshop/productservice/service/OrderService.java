package com.mshop.productservice.service;

import com.mshop.productservice.entity.Order;

import java.util.List;
import java.util.Optional;


public interface OrderService {
	public List<Order> findAllOrderDesc();
	
	public List<Order> findAllOrderByUserId(Long id);
	
	public List<Order> findAllOrderWait();
	
	public List<Order> findAllOrderCancelByUserId(Long id);
	
	public List<Order> findAllOrderWaitByUserId(Long id);
	
	public List<Order> findAllOrderConfirmedByUserId(Long id);
	
	public List<Order> findAllOrderPaidByUserId(Long id);
	
	public List<Object[]> getStatisticalMonthYear(int year);
	
	public List<Object[]> getStatisticalYear();
	
	public List<Object[]> getStatisticalMonth();
	
	public List<Object[]> getStatisticalDate();
	
	public List<Integer> getYears();
	
	public Order save(Order cart);
	
	public Boolean existsById(Long id);
	
	public Optional<Order> findById(Long id);
}
