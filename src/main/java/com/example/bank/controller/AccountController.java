package com.example.bank.controller;

import com.example.bank.assembler.AccountDtoAssembler;
import com.example.bank.model.Account;
import com.example.bank.model.dto.AccountCreateDto;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final AccountDtoAssembler accountDtoAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDto createFish(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        Account account = accountService.create(accountCreateDto);
        return accountDtoAssembler.toModel(account);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDto findById(@PathVariable("id") Long id) {
        Account account = accountService.findBy(id);
        return accountDtoAssembler.toModel(account);
    }
}
