package ru.ar.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ar.account.Entity.Account;
import ru.ar.account.exception.AccountNotFountException;
import ru.ar.account.repository.AccountRepository;

import java.time.OffsetDateTime;
import java.util.List;


@Service
public class AccountService {

    private final AccountRepository accountRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFountException("This account not found" + accountId));
    }
    public Long createAccount(String name, String email, String phone, List<Long> bills ) {
        Account account = new Account(name, email, phone, OffsetDateTime.now(), bills);
        return accountRepository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, String name, String email, String phone, List<Long> bills) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setName(name);
        account.setEmail(email);
        account.setPhone(phone);
        account.setBills(bills);
        return accountRepository.save(account);
    }
    public Account deleteAccount(Long accountId) {
        Account delete = findAccountById(accountId);
        accountRepository.deleteById(accountId);
        return delete;
    }
}
