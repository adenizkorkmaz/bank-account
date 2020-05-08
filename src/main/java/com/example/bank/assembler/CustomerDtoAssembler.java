package com.example.bank.assembler;

import com.example.bank.controller.CustomerController;
import com.example.bank.model.User;
import com.example.bank.model.dto.CustomerResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDtoAssembler extends RepresentationModelAssemblerSupport<User, CustomerResponseDto> {

    private final AccountDtoAssembler accountDtoAssembler;

    public CustomerDtoAssembler(AccountDtoAssembler accountDtoAssembler) {
        super(CustomerController.class, CustomerResponseDto.class);
        this.accountDtoAssembler = accountDtoAssembler;
    }

    @Override
    public CustomerResponseDto toModel(User entity) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        BeanUtils.copyProperties(entity, customerResponseDto);
        entity.getAccounts().forEach(account -> customerResponseDto.addAccount(accountDtoAssembler.toModel(account)));
        customerResponseDto.add(linkTo(methodOn(CustomerController.class).findById(customerResponseDto.getId())).withSelfRel());
        return customerResponseDto;
    }

    @Override
    public CollectionModel<CustomerResponseDto> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<CustomerResponseDto> customerResponseDtos = super.toCollectionModel(entities);
        customerResponseDtos.add(linkTo(methodOn(CustomerController.class).findAll()).withSelfRel());
        return customerResponseDtos;
    }
}
