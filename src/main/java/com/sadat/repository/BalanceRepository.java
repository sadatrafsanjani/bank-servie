package com.sadat.repository;

import com.sadat.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Transactional
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    List<Balance> findBalanceByAccount_AccountNo(String accountNo);

    @Query("SELECT MAX(b.transactionDatetime) FROM Balance b WHERE b.account.accountNo = :accountNo")
    Instant findLastTransactionTimeByAccountNo(String accountNo);

    @Query("SELECT b FROM Balance b WHERE b.transactionDatetime = :instant")
    Balance findLastBalanceByTransactionTime(Instant instant);
}
