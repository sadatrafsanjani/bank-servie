package com.sadat.dto;

import lombok.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BalanceResponse {

    private String accountHolder;
    private String accountNo;
    private double credit;
    private double debit;
    private double balance;
    private Instant transactionDatetime;
    private String description;
}
