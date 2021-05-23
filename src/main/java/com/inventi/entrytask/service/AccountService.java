package com.inventi.entrytask.service;

import com.inventi.entrytask.controller.DateFromToRequest;
import com.inventi.entrytask.entity.Account;
import com.inventi.entrytask.entity.AccountTransaction;
import com.inventi.entrytask.repository.AccountRepository;
import com.inventi.entrytask.repository.AccountTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    public AccountService(AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }


    public Account addNewAccount(Account account) {
        Account newAccount = new Account();
        SetCustomAccountData(account, newAccount);
        return accountRepository.save(newAccount);
    }

    private void SetCustomAccountData(Account account, Account newAccount) {
        StringBuilder stringBuilder = generateRandomAccountNumber(account);
        newAccount.setId(UUID.randomUUID());
        newAccount.setFullName(account.getFullName());
        newAccount.setAccountNumber(stringBuilder.toString());
    }

    private StringBuilder generateRandomAccountNumber(Account account) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DAN");
        stringBuilder.append(account.getFullName().length());
        stringBuilder.append(LocalDate.now().getYear());
        stringBuilder.append(account.getFullName().length());
        return stringBuilder;
    }

    public Double balance(DateFromToRequest dateFromToRequest, String accountNumber) {

        var accountTransactions = accountTransactionRepository.findByAccountNumbers(accountNumber);
        if(accountTransactions != null){
            if(dateFromToRequest != null){
                var dateFrom = dateFromToRequest.getDateFrom();
                var dateTo = dateFromToRequest.getDateTo();
                accountTransactions = sortByDateFrom(accountTransactions, dateFrom);
                accountTransactions = sortByDateTo(accountTransactions, dateTo);
            }
        }
        return accountTransactions.stream()
                .mapToDouble(x -> x.getAmount()).sum();
    }

    private List<AccountTransaction> sortByDateTo(List<AccountTransaction> accountTransactions, LocalDate dateTo) {
        if(dateTo != null){
            accountTransactions = accountTransactions.stream().filter(accountTransaction -> {
                return accountTransaction.getOperationDate().isBefore(dateTo.plusDays(1));
            }).collect(Collectors.toList());
        }
        return accountTransactions;
    }

    private List<AccountTransaction> sortByDateFrom(List<AccountTransaction> accountTransactions, LocalDate dateFrom) {
        if(dateFrom != null){
        accountTransactions = accountTransactions.stream().filter(accountTransaction -> {
            return accountTransaction.getOperationDate().isAfter(dateFrom.minusDays(1));
        }).collect(Collectors.toList());
        }
        return accountTransactions;
    }
}
