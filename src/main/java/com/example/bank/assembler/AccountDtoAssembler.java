package com.example.bank.assembler;

import com.example.bank.controller.AccountController;
import com.example.bank.controller.CustomerController;
import com.example.bank.model.Account;
import com.example.bank.model.dto.AccountResponseDto;
import com.example.bank.model.dto.TransactionResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountDtoAssembler extends RepresentationModelAssemblerSupport<Account, AccountResponseDto> {

    public AccountDtoAssembler() {
        super(AccountController.class, AccountResponseDto.class);
    }

    @Override
    public AccountResponseDto toModel(Account account) {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        BeanUtils.copyProperties(account, accountResponseDto);
        accountResponseDto.setUserId(account.getUser().getId());
        accountResponseDto.setTransactions(getTransactionResponse(account));
        accountResponseDto.add(linkTo(methodOn(AccountController.class).findById(accountResponseDto.getId())).withSelfRel());
        return accountResponseDto;
    }

    @Override
    public CollectionModel<AccountResponseDto> toCollectionModel(Iterable<? extends Account> entities) {
        CollectionModel<AccountResponseDto> accountResponseDtos = super.toCollectionModel(entities);
        accountResponseDtos.add(linkTo(methodOn(CustomerController.class).findAll()).withSelfRel());
        return accountResponseDtos;
    }

    private List<TransactionResponseDto> getTransactionResponse(Account account) {
        return account.getTransactions().stream().map(transaction -> {
            TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
            BeanUtils.copyProperties(transaction, transactionResponseDto);
            transactionResponseDto.setAccountId(transaction.getAccount().getId());
            return transactionResponseDto;
        }).collect(Collectors.toList());
    }


}
