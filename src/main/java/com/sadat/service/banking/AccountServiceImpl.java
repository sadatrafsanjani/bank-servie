package com.sadat.service.banking;

import com.sadat.model.Account;
import com.sadat.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account){

        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByNo(String accountNo){

        return accountRepository.findByAccountNo(accountNo);
    }

    @Override
    public String generateAccountNumber(){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return String.valueOf(timestamp.getTime());
    }
}
