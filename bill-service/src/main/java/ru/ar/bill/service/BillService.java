package ru.ar.bill.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ar.bill.exception.BillNotFoundException;
import ru.ar.bill.repository.BillRepository;
import ru.ar.bill.entity.Bill;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getBill(Long id) {
        return billRepository.findById(id).orElseThrow(() -> new BillNotFoundException("Bill is not found" + id));
    }

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDefault, boolean overdraftEnabled){
        Bill bill = new Bill(accountId, amount, isDefault, OffsetDateTime.now(), overdraftEnabled);
        return billRepository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount, Boolean isDefault, boolean overdraftEnabled){
        Bill bill = new Bill(accountId,amount, isDefault,overdraftEnabled);
        bill.setBillId(billId);
        return billRepository.save(bill);
    }
    public Bill deleteBill(Long billId){
        Bill bill = getBill(billId);
        billRepository.delete(bill);
        return bill;
    }

    public List<Bill> findtoBillAccountID(Long accountId){
        return billRepository.getBillsByAccountId(accountId);
    }
}
