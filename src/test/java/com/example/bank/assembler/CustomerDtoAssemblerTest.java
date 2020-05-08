package com.example.bank.assembler;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.model.dto.CustomerResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CustomerDtoAssemblerTest {

    private CustomerDtoAssembler customerDtoAssembler;
    private AccountDtoAssembler accountDtoAssembler;

    @BeforeEach
    public void init() {
        accountDtoAssembler = Mockito.mock(AccountDtoAssembler.class);
        customerDtoAssembler = new CustomerDtoAssembler(accountDtoAssembler);
    }

    @Test
    void toModel() {
        Account account = Account.builder().id(33L).build();

        User customer = User.builder()
                .id(1L)
                .accounts(Collections.singletonList(account))
                .name("name")
                .build();

        AccountResponseDto build = AccountResponseDto.builder().id(33L).build();
        when(accountDtoAssembler.toModel(account)).thenReturn(build);

        CustomerResponseDto customerResponseDto = customerDtoAssembler.toModel(customer);

        assertEquals(1L, customerResponseDto.getId());
        assertEquals("name", customerResponseDto.getName());
        AccountResponseDto accountResponseDto = customerResponseDto.getAccountList().get(0);
        assertEquals(build, accountResponseDto);
        assertEquals(33L, accountResponseDto.getId());
    }

}
