package com.mshop.productservice.controller;

import com.mshop.productservice.entity.Category;
import com.mshop.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping()
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.findByStatusTrue());
    }

    @RequestMapping("{id}")
    public ResponseEntity<Category> getOne(@PathVariable("id") Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryService.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Category> post(@RequestBody Category category) {
        if (categoryService.existsById(category.getCategoryId())) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.clearCache();
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> put(@RequestBody Category category, @PathVariable("id") Long id) {
        if (!id.equals(category.getCategoryId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.clearCache();
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category ca = categoryService.findById(id).get();
        ca.setStatus(false);
        categoryService.save(ca);
//		repo.deleteById(id);
        categoryService.clearCache();
        return ResponseEntity.ok().build();
    }
}
