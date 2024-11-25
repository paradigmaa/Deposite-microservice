package ru.ar.bill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ar.bill.controller.dto.BillRequestDTO;
import ru.ar.bill.controller.dto.BillResponseDTO;
import ru.ar.bill.entity.Bill;
import ru.ar.bill.service.BillService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{billId}")
    public BillResponseDTO getBill(@PathVariable Long billId) {
        return new BillResponseDTO(billService.getBill(billId));
    }

    @PostMapping("/")
    public Long createBill(@RequestBody BillRequestDTO billRequest) {
        return billService.createBill(billRequest.getAccountId(), billRequest.getAmount(), billRequest.getIsDefault(), billRequest.getOverdraftEnabled());
    }
    @PutMapping("/{billId}")
    public BillResponseDTO updateBill(@PathVariable Long billId, @RequestBody BillRequestDTO billRequest) {
        return new BillResponseDTO(billService.updateBill(billId, billRequest.getAccountId(),
                billRequest.getAmount(), billRequest.getIsDefault(), billRequest.getOverdraftEnabled()));
    }
    @DeleteMapping("/{billId}")
    public BillResponseDTO deleteBill(@PathVariable Long billId) {
        return new BillResponseDTO(billService.deleteBill(billId));
    }

    @GetMapping("/account/{accountId}")
    List<BillResponseDTO> getAllBills(@PathVariable  Long accountId) {
        return billService.findtoBillAccountID(accountId).stream().map(BillResponseDTO::new).collect(Collectors.toList());
    }


}
