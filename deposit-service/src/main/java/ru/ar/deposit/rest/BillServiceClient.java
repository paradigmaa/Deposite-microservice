package ru.ar.deposit.rest;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {
    @RequestMapping(value = "bills/{billId}", method = RequestMethod.GET)
    BillResponseDTO getBillById(@PathVariable("billId") Long billId);

    @RequestMapping(value = "bills/{billId}", method = RequestMethod.PUT)
    void Update(@PathVariable("billId") Long billId, @RequestBody BillRequestDTO billRequestDTO);

    @RequestMapping(value = "bills/account/{accountId}", method = RequestMethod.GET)
    List<BillResponseDTO> getBillByAccountId(@PathVariable("accountId") Long accountId);
}
