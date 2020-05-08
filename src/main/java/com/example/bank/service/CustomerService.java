package com.example.bank.service;

import com.example.bank.exception.NotFoundException;
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id :" + id);
                    return new NotFoundException("Not found exception", id);
                });

        log.info("Customer retrieved with id : " + id);
        return user;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
