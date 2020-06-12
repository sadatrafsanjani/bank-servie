package com.sadat.service.banking;

import com.sadat.dto.banking.BalanceResponse;
import com.sadat.dto.banking.TransactionRequest;
import com.sadat.model.Balance;
import java.util.List;

public interface BalanceService {

    void saveBalance(Balance balance);
    Balance debitAccount(TransactionRequest request);
    Balance creditAccount(TransactionRequest request);
    BalanceResponse checkCurrentBalance(String accountNo);
    List<BalanceResponse> checkTransactions(String accountNo);
}
