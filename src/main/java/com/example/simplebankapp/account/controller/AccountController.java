package com.example.simplebankapp.account.controller;

import com.example.simplebankapp.account.dto.AccountDto;
import com.example.simplebankapp.account.dto.DepositDto;
import com.example.simplebankapp.account.model.Account;
import com.example.simplebankapp.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    private ResponseEntity<String> createAccount(@RequestBody AccountDto dto) {
        String accountNumber = accountService.createAccount(dto);
        return ResponseEntity.ok(accountNumber);
    }

    @GetMapping
    private ResponseEntity<List<Account>> createAccount() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("deposit/{accountNumber}/{userId}/{amount}")
    private ResponseEntity<BigDecimal> deposit(@PathVariable String accountNumber, @PathVariable Long userId, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(accountService.deposit(accountNumber, userId, amount));
    }

    @PostMapping("withdraw/{accountNumber}/{userId}/{amount}")
    private ResponseEntity<BigDecimal> withdraw(@PathVariable String accountNumber, @PathVariable Long userId, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(accountService.withdraw(accountNumber, userId, amount));
    }

}
