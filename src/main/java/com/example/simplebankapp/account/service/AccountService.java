package com.example.simplebankapp.account.service;

import com.example.simplebankapp.account.dto.AccountDto;
import com.example.simplebankapp.email.dto.EmailDetails;
import com.example.simplebankapp.account.model.Account;
import com.example.simplebankapp.user.model.User;
import com.example.simplebankapp.account.repository.AccountRepository;
import com.example.simplebankapp.user.repository.UserRepository;
import com.example.simplebankapp.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.simplebankapp.account.utils.AccountUtils.generateAccountNumber;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public String createAccount(AccountDto dto) {

        Long userId = dto.getUserId();
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("No such user found");
        }

        User user = userOpt.get();

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .name(user.getFirstName() + " " + user.getLastName() + " " + dto.getType() + " account")
                .type(dto.getType())
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();


        Account accountDb = accountRepository.save(account);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Account created for user: " + user.getFirstName())
                .messageBody("Congratulations, you have created the account\n" +
                        "Account name: " + accountDb.getName() + "\n" +
                        "Account number: " + accountDb.getAccountNumber())
                .build();
        emailService.sendEmail(emailDetails);
        log.info("Account created: {}", accountDb.getAccountNumber());
        return accountDb.getAccountNumber();
    }

    public BigDecimal deposit(String accountNumber, Long userId, BigDecimal amount) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isEmpty()) {
            log.error("No such account");
            throw new RuntimeException("No such account");
        }
        Account account = accountOpt.get();
        User user = account.getUser();
        if (!Objects.equals(userId, user.getId())) {
            log.error("User id does not match account");
            throw new RuntimeException("User id does not match account");
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        return newBalance.setScale(2, RoundingMode.HALF_DOWN);

    }

    public BigDecimal withdraw(String accountNumber, Long userId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Amount to withdraw cannot be negative");
            throw new RuntimeException("Amount to withdraw cannot be negative");
        }

        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountOpt.isEmpty()) {
            log.error("No such account");
            throw new RuntimeException("No such account");
        }
        Account account = accountOpt.get();
        User user = account.getUser();
        if (!Objects.equals(userId, user.getId())) {
            log.error("User id does not match account");
            throw new RuntimeException("User id does not match account");
        }

        BigDecimal currentBalance = account.getBalance();

        int result = amount.compareTo(currentBalance);
        if (result > 0) {
            log.error("Amount to withdraw exceeds current balance");
            throw new RuntimeException("Amount to withdraw exceeds current balance");
        }

        BigDecimal newBalance = currentBalance.subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        return newBalance.setScale(2, RoundingMode.HALF_DOWN);

    }
}
