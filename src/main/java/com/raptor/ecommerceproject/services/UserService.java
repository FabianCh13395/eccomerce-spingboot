package com.raptor.ecommerceproject.services;

import com.raptor.ecommerceproject.models.User;

import java.util.Optional;

public interface UserService {
         Optional<User> findById(Long id);
        User save(User user);
}
