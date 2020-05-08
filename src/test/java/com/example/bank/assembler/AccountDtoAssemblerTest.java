package com.example.bank.assembler;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.model.User;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.model.dto.TransactionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDtoAssemblerTest {

    private AccountDtoAssembler accountDtoAssembler;

    @BeforeEach
    public void init() {
        accountDtoAssembler = new AccountDtoAssembler();
    }

    @Test
    void toModel() {
        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.TEN)
                .user(User.builder().id(22L).build())
                .build();

        Transaction transaction = Transaction.builder()
                .account(account)
                .transactionType(TransactionType.DEPOSIT)
                .transactionAmount(BigDecimal.TEN).build();

        account.setTransactions(Collections.singletonList(transaction));

        AccountResponseDto accountResponseDto = accountDtoAssembler.toModel(account);

        assertEquals(1L, accountResponseDto.getId());
        assertEquals(BigDecimal.TEN, accountResponseDto.getBalance());
        assertEquals(22L, accountResponseDto.getUserId());

        TransactionResponseDto transactionResponseDto = accountResponseDto.getTransactions().get(0);
        assertEquals(1L, transactionResponseDto.getAccountId());
        assertEquals(TransactionType.DEPOSIT, transactionResponseDto.getTransactionType());
    }

    @Test
    void toCollectionModel() {
        Account account = Account.builder()
                .id(1L)
                .balance(BigDecimal.TEN)
                .user(User.builder().id(22L).build())
                .build();

        Transaction transaction = Transaction.builder()
                .account(account)
                .transactionType(TransactionType.DEPOSIT)
                .transactionAmount(BigDecimal.TEN).build();

        account.setTransactions(Collections.singletonList(transaction));

        CollectionModel<AccountResponseDto> responseDtos = accountDtoAssembler.toCollectionModel(Collections.singletonList(account));

        AccountResponseDto accountResponseDto = responseDtos.getContent().iterator().next();

        assertEquals(1L, accountResponseDto.getId());
        assertEquals(BigDecimal.TEN, accountResponseDto.getBalance());
        assertEquals(22L, accountResponseDto.getUserId());

        TransactionResponseDto transactionResponseDto = accountResponseDto.getTransactions().get(0);
        assertEquals(1L, transactionResponseDto.getAccountId());
        assertEquals(TransactionType.DEPOSIT, transactionResponseDto.getTransactionType());
    }
}
