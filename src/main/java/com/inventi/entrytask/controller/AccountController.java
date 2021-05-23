package com.inventi.entrytask.controller;

import com.inventi.entrytask.entity.Account;
import com.inventi.entrytask.repository.AccountRepository;
import com.inventi.entrytask.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }
    @PostMapping()
    public ResponseEntity<Account> addNewAccount(@RequestBody Account account){
        Account newAccount = accountService.addNewAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @GetMapping()
    public List<Account> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return  accounts;
    }

    @GetMapping("/balance/{accountNumber}")
    public Double accountBalance(@PathVariable String accountNumber, @RequestBody(required = false) DateFromToRequest dateFromToRequest){
        var balance = accountService.balance(dateFromToRequest, accountNumber);

        return balance;
    }
}
