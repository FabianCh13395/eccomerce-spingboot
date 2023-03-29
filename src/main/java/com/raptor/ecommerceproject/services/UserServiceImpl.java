package com.raptor.ecommerceproject.services;

import com.raptor.ecommerceproject.models.User;
import com.raptor.ecommerceproject.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Optional<User>findById(Long id) {
        return userRepository.findById(id);
    }
}
