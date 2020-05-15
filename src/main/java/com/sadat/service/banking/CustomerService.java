package com.sadat.service.banking;

import com.sadat.dto.AccountRequest;
import com.sadat.dto.CustomerRequest;
import com.sadat.dto.CustomerResponse;
import com.sadat.model.Customer;
import java.util.List;

public interface CustomerService {

    Customer saveCustomer(CustomerRequest request);
    List<CustomerResponse> getCustomers();
    CustomerResponse getCustomer(long id);
    CustomerResponse getCustomerByAccountNo(String accountNo);
    boolean checkAccountNo(AccountRequest request);
}
