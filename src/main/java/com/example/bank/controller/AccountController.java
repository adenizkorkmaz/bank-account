package com.example.bank.controller;

import com.example.bank.assembler.AccountDtoAssembler;
import com.example.bank.model.Account;
import com.example.bank.model.dto.AccountCreateDto;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.model.dto.CustomerResponseDto;
import com.example.bank.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;
    private final AccountDtoAssembler accountDtoAssembler;

    @ApiOperation(value = "Create new account for given customer", response = AccountResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created account"),
            @ApiResponse(code = 404, message = "Customer not found"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDto createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        Account account = accountService.create(accountCreateDto);
        return accountDtoAssembler.toModel(account);
    }


    @ApiOperation(value = "View a account by id", response = CustomerResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved account"),
            @ApiResponse(code = 404, message = "The account you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDto findById(@PathVariable("id") Long id) {
        Account account = accountService.findBy(id);
        return accountDtoAssembler.toModel(account);
    }
}
