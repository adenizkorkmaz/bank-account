package com.example.bank.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class AccountCreateDto {

    @NotNull
    private Long customerId;

    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private BigDecimal initialCredit;

}
