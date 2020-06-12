package com.sadat.service.banking;

import com.sadat.dto.banking.AccountRequest;
import com.sadat.dto.banking.CustomerRequest;
import com.sadat.dto.banking.CustomerResponse;
import com.sadat.model.Account;
import com.sadat.model.Balance;
import com.sadat.model.Customer;
import com.sadat.model.Upload;
import com.sadat.repository.BalanceRepository;
import com.sadat.repository.CustomerRepository;
import com.sadat.repository.ProductRepository;
import com.sadat.repository.UploadRepository;
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
    private UploadRepository uploadRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               BalanceRepository balanceRepository,
                               ProductRepository productRepository,
                               AccountService accountService,
                               UploadRepository uploadRepository) {
        this.customerRepository = customerRepository;
        this.balanceRepository = balanceRepository;
        this.productRepository = productRepository;
        this.accountService = accountService;
        this.uploadRepository = uploadRepository;
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
        Customer newCustomer = customerRepository.save(customer);

        Upload upload = new Upload();
        upload.setCustomer(newCustomer);
        upload.setPicture(null);
        upload.setNid(null);
        uploadRepository.save(upload);

        return newCustomer;
    }

    @Override
    public List<CustomerResponse> getCustomers(){

        List<CustomerResponse> responses = new ArrayList<>();

        for(Customer customer : customerRepository.findAll()){

            double balance = getLastBalance(customer.getAccount().getAccountNo());
            responses.add(getCustomerToDto(customer, balance));
        }

        return responses;
    }

    @Override
    public CustomerResponse getCustomer(long id){

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isPresent()){

            Customer customer = customerOptional.get();
            double balance = getLastBalance(customer.getAccount().getAccountNo());

            return getCustomerToDto(customer, balance);
        }

        return null;
    }

    @Override
    public CustomerResponse getCustomerByAccountNo(String accountNo) {

        Customer customer = customerRepository.findByAccount_AccountNo(accountNo);

        if(customer != null){

            double balance = getLastBalance(accountNo);

            return getCustomerToDto(customer, balance);
        }


        return null;
    }

    private double getLastBalance(String accountNo){

        Instant transactionTime = balanceRepository.findLastTransactionTimeByAccountNo(accountNo);
        return balanceRepository.findLastBalanceByTransactionTime(transactionTime).getBalance();
    }

    @Override
    public boolean checkAccountNo(AccountRequest request){

        return customerRepository.findByAccount_AccountNo(request.getAccountNo()) != null;
    }

    private CustomerResponse getCustomerToDto(Customer customer, double balance){

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
                .uploadStatus(checkUploadStatus(customer.getId()))
                .build();
    }

    private boolean checkUploadStatus(long id){

        Upload upload = uploadRepository.findByCustomer_Id(id);

        return ((upload.getNid() != null) && (upload.getPicture() != null));
    }
}
