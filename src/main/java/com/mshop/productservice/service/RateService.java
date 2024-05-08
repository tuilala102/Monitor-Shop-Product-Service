package com.mshop.productservice.service;

import com.mshop.productservice.entity.Rate;

import java.util.List;
import java.util.Optional;

public interface RateService {
    public List<Rate> findByProduct(Long id);

    public Double getAvgByProduct(Long id);

    public Boolean existsById(Long id);

    public Optional<Rate> findById(Long id);

    public Rate save(Rate cart);

    public List<Rate> findAll();

    public void deleteById(Long id);
}
