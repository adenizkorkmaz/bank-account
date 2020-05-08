package com.example.bank.model.dto;

import com.example.bank.model.Transaction;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccountResponseDto extends RepresentationModel<AccountResponseDto> {
    private Long id;

    private Long userId;

    private BigDecimal balance;

    private Boolean current;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private List<TransactionResponseDto> transactions;
}
