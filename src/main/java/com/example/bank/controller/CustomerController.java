package com.example.bank.controller;


import com.example.bank.assembler.CustomerDtoAssembler;
import com.example.bank.model.User;
import com.example.bank.model.dto.CustomerResponseDto;
import com.example.bank.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDtoAssembler customerDtoAssembler;

    @ApiOperation(value = "View all customers", response = CustomerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customers"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CustomerResponseDto> findAll() {
        Iterable<User> all = customerService.findAll();
        return customerDtoAssembler.toCollectionModel(all);
    }

    @ApiOperation(value = "View a customer by id", response = CustomerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer"),
            @ApiResponse(code = 404, message = "The customer you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto findById(@PathVariable("id") Long id) {
        User customer = customerService.findBy(id);
        return customerDtoAssembler.toModel(customer);
    }

}
