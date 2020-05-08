package com.example.bank.service;

import com.example.bank.exception.NotFoundException;
import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private CustomerService customerService;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        customerService = new CustomerService(userRepository);
    }

    @Test
    void findBy_shouldFindSuccessfully() {
        User build = User.builder().build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(build));

        User user = customerService.findBy(1L);

        assertEquals(build, user);
    }

    @Test
    void findBy_shouldFindAll() {
        User build = User.builder().build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(build));

        Iterable<User> all = customerService.findAll();

        assertEquals(build, all.iterator().next());
    }

    @Test
    void findBy_shouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerService.findBy(1L));
    }


}
