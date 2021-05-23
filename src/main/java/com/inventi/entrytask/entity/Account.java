package com.inventi.entrytask.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Account {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String fullName;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
//    @JoinColumn( name = "account_transaction", referencedColumnName = "id")
//    List<AccountTransaction> accountTransactions = new ArrayList<>();


    public Account(){

    }

    public Account(UUID id, String accountNumber, String fullName) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.fullName = fullName;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getFullName() {
        return fullName;
    }
}
