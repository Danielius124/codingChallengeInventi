package com.inventi.entrytask.repository;

import com.inventi.entrytask.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findByAccountNumber(String accountNumber);

    @Query(value = "select * from account where account_number in :accountNumbers", nativeQuery = true)
    List<Account> findByAccountNumbers(@Param("accountNumbers") List<String> accountNumbers);

}
