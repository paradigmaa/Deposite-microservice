package ru.ar.account.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ar.account.Entity.Account;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AccountResponseDTO {

    public AccountResponseDTO(Account account) {
        this.accountId = account.getAccountId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.phone = account.getPhone();
        this.bills = account.getBills();
        this.createdAt = account.getCreatedAt();
    }

    public Long accountId;

    private String name;

    private String email;

    private String phone;

    private List<Long> bills;

    private OffsetDateTime createdAt;
}
