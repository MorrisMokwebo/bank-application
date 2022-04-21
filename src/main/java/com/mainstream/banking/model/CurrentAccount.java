package com.mainstream.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "current_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int accountNumber;
    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "currentAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CurrentAccountTransaction> currentAccountTransactionHistory;
}
