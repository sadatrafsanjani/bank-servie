package com.sadat.service.banking;

import com.sadat.model.Account;

public interface AccountService {

    Account saveAccount(Account account);
    String generateAccountNumber();
    Account findAccountByNo(String accountNo);
}
