package com.inventi.entrytask.service;

import com.inventi.entrytask.CSVhelper.CSVHelper;
import com.inventi.entrytask.controller.DateFromToRequest;
import com.inventi.entrytask.entity.AccountTransaction;
import com.inventi.entrytask.repository.AccountRepository;
import com.inventi.entrytask.repository.AccountTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;

    public AccountTransactionService(AccountTransactionRepository accountTransactionRepository, AccountRepository accountRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountRepository = accountRepository;
    }

    public void save(MultipartFile file) throws IOException {
        var transactions = CSVHelper.csvToTransactions(file.getInputStream());
        var accountNumbers = transactions.stream()
                .map(x -> x.getAccountNumber()).collect(Collectors.toList());
        var accounts = accountRepository.findByAccountNumbers(accountNumbers);
        var accountsMapByNumber = accounts.stream().filter(Objects::nonNull).collect(Collectors.toMap(x -> x.getAccountNumber(), Function.identity()));
        var transactionWithAccounts = transactions.stream().map(x -> {
            if (accountsMapByNumber.get(x.getAccountNumber()) != null) {
                x.setAccount(accountsMapByNumber.get(x.getAccountNumber()));
            }
            return x;
        }).collect(Collectors.toList());
        accountTransactionRepository.saveAll(transactionWithAccounts);
    }

    public void exportCsvFile(PrintWriter printWriter, DateFromToRequest dateFromToRequest) {
        columnNames(printWriter);
        var transactions = accountTransactionRepository.findAll();

        transactions = sortTransactions(dateFromToRequest, transactions);

        writeDataToCsvFile(printWriter, transactions);
    }

    public void exportCsvFileForAccount(PrintWriter writer, DateFromToRequest dateFromToRequest, String accountNumber) {
        columnNames(writer);
        var accountTransactions = accountTransactionRepository.findByAccountNumbers(accountNumber);
        accountTransactions = sortTransactions(dateFromToRequest, accountTransactions);

        writeDataToCsvFile(writer, accountTransactions);
    }

    private List<AccountTransaction> sortTransactions(DateFromToRequest dateFromToRequest, List<AccountTransaction> transactions) {
        if (transactions != null) {
            if (dateFromToRequest != null) {
                var dateFrom = dateFromToRequest.getDateFrom();
                var dateTo = dateFromToRequest.getDateTo();
                transactions = sortByDateFrom(transactions, dateFrom);
                transactions = sortByDateTo(transactions, dateTo);
            }
        }
        return transactions;
    }

    private void columnNames(PrintWriter printWriter) {
        printWriter.write("Account Number, Operation date, Beneficiary, Comment, Amount, Currency\n");
    }

    private void writeDataToCsvFile(PrintWriter printWriter, List<AccountTransaction> transactions) {
        transactions.stream()
                .forEach(transaction -> {
                    printWriter.write(transaction.getAccountNumber() + "," + transaction.getOperationDate() + "," +
                            transaction.getBeneficiary() + "," + transaction.getComment() + "," + transaction.getAmount() +
                            "," + transaction.getCurrency() + "\n");
                });
    }

    private List<AccountTransaction> sortByDateTo(List<AccountTransaction> accountTransactions, LocalDate dateTo) {
        if (dateTo != null) {
            accountTransactions = accountTransactions.stream().filter(accountTransaction -> {
                return accountTransaction.getOperationDate().isBefore(dateTo.plusDays(1));
            }).collect(Collectors.toList());
        }
        return accountTransactions;
    }

    private List<AccountTransaction> sortByDateFrom(List<AccountTransaction> accountTransactions, LocalDate dateFrom) {
        if (dateFrom != null) {
            accountTransactions = accountTransactions.stream().filter(accountTransaction -> {
                return accountTransaction.getOperationDate().isAfter(dateFrom.minusDays(1));
            }).collect(Collectors.toList());
        }
        return accountTransactions;
    }


}