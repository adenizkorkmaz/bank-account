package com.example.bank.model.dto;

import com.example.bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto extends RepresentationModel<AccountResponseDto> {
    private Long id;

    private Long userId;

    private BigDecimal balance;

    private Currency currency;

    private Boolean current;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private List<TransactionResponseDto> transactions;
}
