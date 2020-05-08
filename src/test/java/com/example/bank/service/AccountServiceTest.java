package com.example.bank.service;

import com.example.bank.exception.NotFoundException;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.model.User;
import com.example.bank.model.dto.AccountCreateDto;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        accountService = new AccountService(accountRepository, userRepository);
    }

    @Test
    void create_shouldCreateAccountWithZeroCredit() {
        AccountCreateDto dto = AccountCreateDto.builder()
                .customerId(1L)
                .initialCredit(BigDecimal.ZERO)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().build()));
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        Account account = accountService.create(dto);

        Mockito.verify(accountRepository).save(accountArgumentCaptor.capture());

        Account value = accountArgumentCaptor.getValue();
        Assertions.assertEquals(0, value.getTransactions().size());
        Assertions.assertTrue(value.isCurrent());
    }

    @Test
    void create_shouldCreateAccountWithMoreThanZeroCredit() {
        AccountCreateDto dto = AccountCreateDto.builder()
                .customerId(1L)
                .initialCredit(BigDecimal.ONE)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().build()));
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        Account account = accountService.create(dto);

        Mockito.verify(accountRepository).save(accountArgumentCaptor.capture());

        Account value = accountArgumentCaptor.getValue();
        List<Transaction> transactions = value.getTransactions();
        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals(BigDecimal.ONE, transactions.get(0).getTransactionAmount());
        Assertions.assertEquals(TransactionType.DEPOSIT, transactions.get(0).getTransactionType());
        Assertions.assertTrue(value.isCurrent());
    }

    @Test
    void create_shouldThrowExceptionWhenUserNotFound() {
        AccountCreateDto dto = AccountCreateDto.builder()
                .customerId(1L)
                .initialCredit(BigDecimal.ONE)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.create(dto));

        Mockito.verifyNoInteractions(accountRepository);
    }

    @Test
    void findBy_shouldFindSuccessfully() {
        Account build = Account.builder().build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(build));

        Account account = accountService.findBy(1L);

        assertEquals(build, account);
    }

    @Test
    void findBy_shouldThrowExceptionWhenNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.findBy(1L));
    }


}
