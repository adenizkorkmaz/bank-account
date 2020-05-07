package com.example.bank.repository;

import com.example.bank.model.Account;
import com.example.bank.model.User;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
