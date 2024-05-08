package com.mshop.productservice.repository;

import com.mshop.productservice.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{
	@Query(value = "select * from rates where product_id = ?", nativeQuery = true)
	List<Rate> findByProduct(Long id);
	
	@Query(value = "select AVG(star) from rates where product_id = ?", nativeQuery = true)
	Double getAvgByProduct(Long id);
}
