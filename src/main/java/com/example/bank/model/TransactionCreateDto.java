package com.example.bank.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionCreateDto {

    private Account account;

    private TransactionType transactionType;

    private BigDecimal transactionAmount;

    private LocalDateTime transactionTime;

    private String message;
}
