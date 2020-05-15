package com.sadat.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "balances")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "DEBIT")
    private double debit;

    @Column(name = "CREDIT")
    private double credit;

    @Column(name = "BALANCE")
    private double balance;

    @Column(name = "TRANSACTION_DATETIME")
    private Instant transactionDatetime;

    @Column(name = "DESCRIPTION")
    private String description;
}
