package com.sadat.service.banking;

import com.sadat.dto.AccountRequest;
import com.sadat.dto.CustomerRequest;
import com.sadat.dto.CustomerResponse;
import com.sadat.model.Account;
import com.sadat.model.Balance;
import com.sadat.model.Customer;
import com.sadat.repository.BalanceRepository;
import com.sadat.repository.CustomerRepository;
import com.sadat.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private BalanceRepository balanceRepository;
    private ProductRepository productRepository;
    private AccountService accountService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               BalanceRepository balanceRepository,
                               ProductRepository productRepository,
                               AccountService accountService) {
        this.customerRepository = customerRepository;
        this.balanceRepository = balanceRepository;
        this.productRepository = productRepository;
        this.accountService = accountService;
    }

    @Override
    public Customer saveCustomer(CustomerRequest request){

        Account account = new Account();
        account.setProduct(productRepository.getOne(request.getProductId()));
        account.setAccountNo(accountService.generateAccountNumber());
        Account newAccount = accountService.saveAccount(account);

        Balance balance = new Balance();
        balance.setAccount(newAccount);
        balance.setDebit(0.0);
        balance.setCredit(0.0);
        balance.setBalance(0.0);
        balance.setDescription("Account Opening");
        balance.setTransactionDatetime(Instant.now());
        balanceRepository.save(balance);

        Customer customer = new Customer();
        customer.setAccount(newAccount);
        customer.setFullName(request.getFullName());
        customer.setDob(request.getDob());
        customer.setFatherName(request.getFatherName());
        customer.setMotherName(request.getMotherName());
        customer.setNidNo(request.getNidNo());
        customer.setPhoneNo(request.getPhoneNo());
        customer.setAddress(request.getAddress());

        return customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> getCustomers(){

        List<CustomerResponse> responses = new ArrayList<>();

        for(Customer customer : customerRepository.findAll()){

            CustomerResponse response = CustomerResponse.builder()
                    .id(customer.getId())
                    .accountTypeId(customer.getAccount().getProduct().getNature().getId())
                    .accountNo(customer.getAccount().getAccountNo())
                    .accountType(customer.getAccount().getProduct().getName())
                    .fullName(customer.getFullName())
                    .dob(customer.getDob())
                    .fatherName(customer.getFatherName())
                    .motherName(customer.getMotherName())
                    .nidNo(customer.getNidNo())
                    .phoneNo(customer.getPhoneNo())
                    .build();

            responses.add(response);
        }

        return responses;
    }

    @Override
    public CustomerResponse getCustomer(long id){

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isPresent()){

            Customer customer = customerOptional.get();
            double balance = getLastBalance(customer.getAccount().getAccountNo());

            return CustomerResponse.builder()
                    .id(customer.getId())
                    .accountTypeId(customer.getAccount().getProduct().getNature().getId())
                    .accountNo(customer.getAccount().getAccountNo())
                    .accountType(customer.getAccount().getProduct().getName())
                    .fullName(customer.getFullName())
                    .dob(customer.getDob())
                    .fatherName(customer.getFatherName())
                    .motherName(customer.getMotherName())
                    .nidNo(customer.getNidNo())
                    .address(customer.getAddress())
                    .phoneNo(customer.getPhoneNo())
                    .balance(balance)
                    .build();
        }

        return null;
    }

    @Override
    public CustomerResponse getCustomerByAccountNo(String accountNo) {

        Customer customer = customerRepository.findByAccount_AccountNo(accountNo);

        if(customer != null){

            double balance = getLastBalance(accountNo);

            return CustomerResponse.builder()
                    .id(customer.getId())
                    .accountTypeId(customer.getAccount().getProduct().getNature().getId())
                    .accountNo(customer.getAccount().getAccountNo())
                    .accountType(customer.getAccount().getProduct().getName())
                    .fullName(customer.getFullName())
                    .dob(customer.getDob())
                    .fatherName(customer.getFatherName())
                    .motherName(customer.getMotherName())
                    .nidNo(customer.getNidNo())
                    .address(customer.getAddress())
                    .phoneNo(customer.getPhoneNo())
                    .balance(balance)
                    .build();
        }


        return null;
    }

    private double getLastBalance(String accountNo){

        Instant transactionTime = balanceRepository.findLastTransactionTimeByAccountNo(accountNo);
        double balance = balanceRepository.findLastBalanceByTransactionTime(transactionTime).getBalance();

        return balance;
    }

    @Override
    public boolean checkAccountNo(AccountRequest request){

        return (customerRepository.findByAccount_AccountNo(request.getAccountNo()) != null ) ? true : false;
    }
}
