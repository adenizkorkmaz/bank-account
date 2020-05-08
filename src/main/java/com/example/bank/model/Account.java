package com.example.bank.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal balance;

    private boolean current;

    private String accountNumber;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

}
