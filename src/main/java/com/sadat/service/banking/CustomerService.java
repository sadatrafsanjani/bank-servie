package com.sadat.service.banking;

import com.sadat.dto.banking.AccountRequest;
import com.sadat.dto.banking.CustomerRequest;
import com.sadat.dto.banking.CustomerResponse;
import com.sadat.model.Customer;
import java.util.List;

public interface CustomerService {

    Customer saveCustomer(CustomerRequest request);
    List<CustomerResponse> getCustomers();
    CustomerResponse getCustomer(long id);
    CustomerResponse getCustomerByAccountNo(String accountNo);
    boolean checkAccountNo(AccountRequest request);
}
