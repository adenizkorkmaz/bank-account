package com.example.bank.service;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CustomerService {

    private final UserRepository userRepository;


    public User findBy(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found exception"));
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
