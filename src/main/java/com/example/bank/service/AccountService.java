package com.example.bank.service;

import com.example.bank.exception.NotFoundException;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.model.User;
import com.example.bank.model.dto.AccountCreateDto;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Transactional
    public Account create(AccountCreateDto dto) {
        User customer = userRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> {
                    log.error("Account creation : User not found with id :" + dto.getCustomerId());
                    return new NotFoundException("User cannot be found", dto.getCustomerId());
                });


        customer.getAccounts().forEach(account -> account.setCurrent(false));

        Account account = new Account();
        account.setBalance(dto.getInitialCredit());
        account.setCreatedAt(LocalDateTime.now());
        account.setLastUpdated(LocalDateTime.now());
        account.setCurrent(true);
        account.setUser(customer);

        if (dto.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction build = Transaction.builder()
                    .account(account)
                    .transactionType(TransactionType.DEPOSIT)
                    .transactionAmount(account.getBalance())
                    .transactionTime(LocalDateTime.now())
                    .message("Account created")
                    .build();

            account.getTransactions().add(build);
        }

        Account save = accountRepository.save(account);
        log.info("Account is created successfully with id : " + save.getId());
        return save;
    }

    public Account findBy(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Account not found with id : " + id);
                    return new NotFoundException("Account cannot be found", id);
                });

        log.info("Account retrieved with id : " + id);
        return account;
    }
}
