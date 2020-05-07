package com.example.bank.controller;


import com.example.bank.assembler.CustomerDtoAssembler;
import com.example.bank.model.User;
import com.example.bank.model.dto.CustomerResponseDto;
import com.example.bank.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDtoAssembler customerDtoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CustomerResponseDto> findAll() {
        Iterable<User> all = customerService.findAll();
        return customerDtoAssembler.toCollectionModel(all);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable("id") Long id) {
        User customer = customerService.findBy(id);
        return customerDtoAssembler.toModel(customer);
    }

}
