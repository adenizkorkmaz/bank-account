package com.example.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.util.CollectionUtils;

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

    private List<AccountResponseDto> accountList;

    public void addAccount(AccountResponseDto accountResponseDto) {
        if (CollectionUtils.isEmpty(accountList)) {
            accountList = new ArrayList<>();
        }
        accountList.add(accountResponseDto);
    }
}

