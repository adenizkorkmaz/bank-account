package com.example.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDto extends RepresentationModel<CustomerResponseDto> {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private CollectionModel<AccountResponseDto> accounts;
}

