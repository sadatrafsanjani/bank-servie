package com.sadat.repository;

import com.sadat.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByAccount_AccountNo(String accountNo);
}
