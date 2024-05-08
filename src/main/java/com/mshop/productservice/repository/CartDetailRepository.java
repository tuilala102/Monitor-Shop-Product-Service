package com.mshop.productservice.repository;

import com.mshop.productservice.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
	@Query(value = "select * from cart_details where cart_id = ?", nativeQuery = true)
	List<CartDetail> getByCartId(Long id);
	
	@Query(value = "select COUNT(cart_id) from cart_details where cart_id = ?", nativeQuery = true)
	Long getCount(Long id);
}
