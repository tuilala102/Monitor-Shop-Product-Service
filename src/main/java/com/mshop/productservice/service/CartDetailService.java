package com.mshop.productservice.service;

import com.mshop.productservice.entity.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {

    public List<CartDetail> getByCartId(Long id);

    public Long getCount(Long id);

    public CartDetail save(CartDetail cartDetail);

    public Boolean existsById(Long id);

    public Optional<CartDetail> findById(Long id);

    public void deleteById(Long id);
}
