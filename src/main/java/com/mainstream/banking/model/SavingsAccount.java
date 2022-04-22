package com.mainstream.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "savings_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavingsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int accountNumber;
    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SavingsAccountTransaction> savingsAccountTransactionHistory;

    @Enumerated(EnumType.STRING)
    private Status accountStatus;
}
