package ru.ar.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ar.deposit.controller.dto.DepositRequestDTO;
import ru.ar.deposit.controller.dto.DepositResponseDTO;
import ru.ar.deposit.entity.Deposit;
import ru.ar.deposit.service.DepositService;

@RestController
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/deposits")
    public DepositResponseDTO deposit(@RequestBody DepositRequestDTO depositRequestDTO){
        return depositService.deposit(depositRequestDTO.getAccountId(), depositRequestDTO.getBillId(), depositRequestDTO.getAmount());
    }
}
