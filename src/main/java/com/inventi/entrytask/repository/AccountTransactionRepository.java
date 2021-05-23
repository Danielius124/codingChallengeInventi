package com.inventi.entrytask.repository;

import com.inventi.entrytask.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, UUID> {

    List<AccountTransaction> findByAccountId(UUID id);

    @Query(value = "select * from account_transaction where account_number = ?1", nativeQuery = true)
    List<AccountTransaction> findByAccountNumbers(@Param("accountNumbers") String accountNumbers);

    AccountTransaction findByAccountNumber(String accountNumber);
}
