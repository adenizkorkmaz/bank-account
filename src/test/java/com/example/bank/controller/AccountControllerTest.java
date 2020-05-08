package com.example.bank.controller;

import com.example.bank.assembler.AccountDtoAssembler;
import com.example.bank.model.Account;
import com.example.bank.model.dto.AccountCreateDto;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountDtoAssembler accountDtoAssembler;

    @MockBean
    private AccountService accountService;

    @Test
    void createAccount() throws Exception {
        Account account = Account.builder().build();

        when(accountService.create(any(AccountCreateDto.class))).thenReturn(account);

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(1L)
                .balance(BigDecimal.TEN)
                .userId(11L)
                .build();
        when(accountDtoAssembler.toModel(account)).thenReturn(accountResponseDto);

        mockMvc.perform(post("/accounts")
                .contentType("application/json").content("{\n" +
                        "  \"customerId\":\"1\",\n" +
                        "  \"initialCredit\" : 10\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(10))
                .andExpect(jsonPath("$.userId").value(11));
    }

    @Test
    void findById() throws Exception {
        Account account = Account.builder().build();

        when(accountService.findBy(1L)).thenReturn(account);

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(1L)
                .balance(BigDecimal.TEN)
                .userId(11L)
                .build();
        when(accountDtoAssembler.toModel(account)).thenReturn(accountResponseDto);

        mockMvc.perform(get("/accounts/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.balance").value(10))
                .andExpect(jsonPath("$.userId").value(11));
    }
}
