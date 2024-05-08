package com.mshop.productservice.client;

import com.mshop.productservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "UserClient", url = "${auth-service.url}")
public interface UserClient {

    @GetMapping("/users/exist-by-user-id")
    Boolean existsById(@RequestParam("userId") Long userId);

    @GetMapping("/users/{id}")
    User getOne(@PathVariable("id") Long id);

    @PostMapping("/users/get-all-by-ids")
    public List<User> getAllByIds(@RequestBody List<Long> ids);

}
