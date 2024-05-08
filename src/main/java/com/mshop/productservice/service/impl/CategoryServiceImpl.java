package com.mshop.productservice.service.impl;

import com.mshop.productservice.entity.Category;
import com.mshop.productservice.repository.CategoryRepository;
import com.mshop.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
    private CategoryRepository categoryRepository;

	@Cacheable(cacheNames = "monitor.shop.categories", keyGenerator = "redisKeyGenerator")
	// redis caching
	public List<Category> findByStatusTrue() {
        return categoryRepository.findByStatusTrue();
    }
	
	@Transactional
	public Category save(Category cate) {
        return categoryRepository.save(cate);
    }
	
	public Boolean existsById(Long id) {
		return categoryRepository.existsById(id);
	}
	
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	@CacheEvict(cacheNames = "monitor.shop.categories", keyGenerator = "redisKeyGenerator")
	public void clearCache() {
	}

}
