package com.sadat.controller;

import com.sadat.dto.banking.AccountRequest;
import com.sadat.dto.banking.CustomerRequest;
import com.sadat.dto.banking.CustomerResponse;
import com.sadat.service.banking.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers(){

        List<CustomerResponse> response = customerService.getCustomers();

        if(!response.isEmpty()){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") long id){

        CustomerResponse response = customerService.getCustomer(id);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/customer/account/{accountNo}")
    public ResponseEntity<CustomerResponse> getCustomerByAccountNo(@PathVariable("accountNo") String accountNo){

        CustomerResponse response = customerService.getCustomerByAccountNo(accountNo);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerRequest request){

        if(customerService.saveCustomer(request) != null){

            return ResponseEntity.ok("Customer Created Successfully!");
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/customer/account/validity")
    public ResponseEntity checkAccountNo(@Valid @RequestBody AccountRequest request){

        if(customerService.checkAccountNo(request)){

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
