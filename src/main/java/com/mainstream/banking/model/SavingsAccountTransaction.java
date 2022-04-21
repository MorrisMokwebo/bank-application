package com.mainstream.banking.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "savings_account_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavingsAccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String description;
    private String type;
    private String status;
    private double amount;
    private BigDecimal availableBalance;


    public SavingsAccountTransaction(Date date, String description, String type, String status, double amount, BigDecimal availableBalance, SavingsAccount savingsAccount) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.availableBalance = availableBalance;
       this.savingsAccount = savingsAccount;
    }

    @ManyToOne
    @JoinColumn(name = "savings_account_id")
    private SavingsAccount savingsAccount;

}

