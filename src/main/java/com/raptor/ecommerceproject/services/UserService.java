package com.raptor.ecommerceproject.services;

import com.raptor.ecommerceproject.models.User;

import java.util.Optional;

public interface UserService {
        public Optional<User> findById(Long id);
}
