package com.mshop.productservice.repository;

import com.mshop.productservice.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
	@Query(value = "select * from order_details where order_id = ?", nativeQuery = true)
	List<OrderDetail> findOrderDetailByOrderId(Long id);
}
