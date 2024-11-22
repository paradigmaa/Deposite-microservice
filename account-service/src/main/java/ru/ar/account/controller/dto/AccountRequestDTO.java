package ru.ar.account.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AccountRequestDTO {

    public Long accountId;

    private String name;

    private String email;

    private String phone;

    private List<Long> bills;

    private OffsetDateTime createdAt;

}
