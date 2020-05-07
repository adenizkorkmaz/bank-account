package com.example.bank.model.dto;

import com.example.bank.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;

    private Long accountId;

    private TransactionType transactionType;

    private BigDecimal transactionAmount;

    private LocalDateTime transactionTime;

    private String message;

}
