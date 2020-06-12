package com.sadat.service.banking;

import com.sadat.dto.banking.BalanceResponse;
import com.sadat.dto.banking.TransactionRequest;
import com.sadat.model.Account;
import com.sadat.model.Balance;
import com.sadat.model.Customer;
import com.sadat.repository.AccountRepository;
import com.sadat.repository.BalanceRepository;
import com.sadat.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService {

    private BalanceRepository balanceRepository;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository,
                              AccountRepository accountRepository,
                              CustomerRepository customerRepository) {
        this.balanceRepository = balanceRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void saveBalance(Balance balance){

        balanceRepository.save(balance);
    }

    @Override
    public Balance creditAccount(TransactionRequest request){

        Account account = accountRepository.findByAccountNo(request.getAccountNo());

        if((account != null) && (request.getAmount() > 0.0)){

            Instant lastTransactionTime = balanceRepository.findLastTransactionTimeByAccountNo(request.getAccountNo());
            Balance lastBalance = balanceRepository.findLastBalanceByTransactionTime(lastTransactionTime);
            double lastAmount = lastBalance.getBalance();
            double updatedAmount = lastAmount + request.getAmount();

            Balance balance = new Balance();
            balance.setAccount(account);
            balance.setCredit(request.getAmount());
            balance.setDebit(0.0);
            balance.setBalance(updatedAmount);
            balance.setDescription("Account credited by " + request.getAmount() + " BDT");
            balance.setTransactionDatetime(Instant.now());

            return balanceRepository.save(balance);
        }

        return null;
    }

    @Override
    public Balance debitAccount(TransactionRequest request){

        Account account = accountRepository.findByAccountNo(request.getAccountNo());

        if((account != null) && (request.getAmount() > 0.0)){

            Instant lastTransactionTime = balanceRepository.findLastTransactionTimeByAccountNo(request.getAccountNo());
            Balance lastBalance = balanceRepository.findLastBalanceByTransactionTime(lastTransactionTime);
            double lastAmount = lastBalance.getBalance();

            if(lastAmount >= request.getAmount()){

                double updatedBalance = lastAmount - request.getAmount();

                Balance balance = new Balance();
                balance.setAccount(account);
                balance.setCredit(0.0);
                balance.setDebit(request.getAmount());
                balance.setBalance(updatedBalance);
                balance.setTransactionDatetime(Instant.now());
                balance.setDescription("Account debited by " + request.getAmount() + " BDT");

                return balanceRepository.save(balance);
            }
        }

        return null;
    }

    @Override
    public BalanceResponse checkCurrentBalance(String accountNo){

        Account account = accountRepository.findByAccountNo(accountNo);

        if(account != null){

            Customer customer = customerRepository.findByAccount_AccountNo(accountNo);

            Instant lastTransactionTime = balanceRepository.findLastTransactionTimeByAccountNo(accountNo);
            Balance lastBalance = balanceRepository.findLastBalanceByTransactionTime(lastTransactionTime);

            return BalanceResponse.builder()
                    .accountHolder(customer.getFullName())
                    .accountNo(accountNo)
                    .credit(lastBalance.getCredit())
                    .debit(lastBalance.getDebit())
                    .balance(lastBalance.getBalance())
                    .transactionDatetime(lastBalance.getTransactionDatetime())
                    .description(lastBalance.getDescription())
                    .build();
        }

        return null;
    }

    @Override
    public List<BalanceResponse> checkTransactions(String accountNo){

        List<BalanceResponse> responses = new ArrayList<>();

        for(Balance balance : balanceRepository.findBalanceByAccount_AccountNo(accountNo)){

            Customer customer = customerRepository.findByAccount_AccountNo(accountNo);

            BalanceResponse response = BalanceResponse.builder()
                    .accountHolder(customer.getFullName())
                    .accountNo(accountNo)
                    .debit(balance.getDebit())
                    .credit(balance.getCredit())
                    .balance(balance.getBalance())
                    .transactionDatetime(balance.getTransactionDatetime())
                    .description(balance.getDescription())
                    .build();

            responses.add(response);
        }

        return responses;
    }
}
