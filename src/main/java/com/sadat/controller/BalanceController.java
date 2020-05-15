package com.sadat.controller;

import com.sadat.dto.BalanceResponse;
import com.sadat.dto.TransactionRequest;
import com.sadat.service.banking.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/debit")
    public ResponseEntity debitAccount(@Valid @RequestBody TransactionRequest request){

        if(balanceService.debitAccount(request) != null){

            return ResponseEntity.ok("Account Debited by " + request.getAmount() + " BDT");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/credit")
    public ResponseEntity creditAccount(@Valid @RequestBody TransactionRequest request){

        if(balanceService.creditAccount(request) != null){

            return ResponseEntity.ok("Account Credited by " + request.getAmount() + " BDT");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/balance/{accountNo}")
    public ResponseEntity checkCurrentBalance(@PathVariable("accountNo") String accountNo){

        BalanceResponse response = balanceService.checkCurrentBalance(accountNo);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/history/{accountNo}")
    public ResponseEntity checkTransactions(@PathVariable("accountNo") String accountNo){

        List<BalanceResponse> response = balanceService.checkTransactions(accountNo);

        if(!response.isEmpty()){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
}
