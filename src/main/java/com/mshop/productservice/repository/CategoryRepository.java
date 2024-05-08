package com.mshop.productservice.repository;

import com.mshop.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findByStatusTrue();
}
