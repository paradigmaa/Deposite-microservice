package ru.ar.deposit.controller.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequestDTO {

    Long accountId;

    private Long billId;

    private BigDecimal amount;
}
