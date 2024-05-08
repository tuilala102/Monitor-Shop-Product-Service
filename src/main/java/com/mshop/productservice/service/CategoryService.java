package com.mshop.productservice.service;

import com.mshop.productservice.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
	public List<Category> findByStatusTrue();
	
	public Category save(Category cartDetail);
	
	public Boolean existsById(Long id);
	
	public Optional<Category> findById(Long id);
	
	public void deleteById(Long id);

	void clearCache();

}
