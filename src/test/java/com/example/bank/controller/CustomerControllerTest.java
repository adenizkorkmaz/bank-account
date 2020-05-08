package com.example.bank.controller;

import com.example.bank.assembler.CustomerDtoAssembler;
import com.example.bank.model.TransactionType;
import com.example.bank.model.User;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.model.dto.CustomerResponseDto;
import com.example.bank.model.dto.TransactionResponseDto;
import com.example.bank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerDtoAssembler customerDtoAssembler;

    @MockBean
    private CustomerService customerService;

    @Test
    void findById() throws Exception {
        User customer = User.builder().build();
        when(customerService.findBy(1L)).thenReturn(customer);

        TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                .transactionType(TransactionType.DEPOSIT)
                .build();

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(33L)
                .balance(BigDecimal.TEN)
                .transactions(Collections.singletonList(transactionResponseDto))
                .build();

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .name("name")
                .id(1L)
                .accountList(Collections.singletonList(accountResponseDto))
                .build();

        when(customerDtoAssembler.toModel(customer)).thenReturn(customerResponseDto);

        mockMvc.perform(get("/customers/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.accountList[:1].id").value(33))
                .andExpect(jsonPath("$.accountList[:1].balance").value(10))
                .andExpect(jsonPath("$.accountList[:1].transactions[:1].transactionType").value("DEPOSIT"));
    }

    @Test
    void findAll() throws Exception {
        User customer = User.builder().build();
        List<User> userList = Collections.singletonList(customer);
        when(customerService.findAll()).thenReturn(userList);

        TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                .transactionType(TransactionType.DEPOSIT)
                .build();

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(33L)
                .balance(BigDecimal.TEN)
                .transactions(Collections.singletonList(transactionResponseDto))
                .build();

        CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                .name("name")
                .id(1L)
                .accountList(Collections.singletonList(accountResponseDto))
                .build();

        when(customerDtoAssembler.toCollectionModel(userList))
                .thenReturn(new CollectionModel<>(Collections.singletonList(customerResponseDto)));

        mockMvc.perform(get("/customers")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("name"))
                .andExpect(jsonPath("$.content[0].accountList[:1].id").value(33))
                .andExpect(jsonPath("$.content[0].accountList[:1].balance").value(10))
                .andExpect(jsonPath("$.content[0].accountList[:1].transactions[:1].transactionType").value("DEPOSIT"));
    }

}
