package ru.ar.deposit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ar.deposit.controller.dto.DepositResponseDTO;
import ru.ar.deposit.entity.Deposit;
import ru.ar.deposit.exception.DepositServiceException;
import ru.ar.deposit.repository.DepositRepository;
import ru.ar.deposit.rest.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class DepositService {

    public static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    public static final String ROUTING_KEY_DEPOSIT = "js.key.notify";

    private final DepositRepository depositRepository;

    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DepositService(DepositRepository depositRepository, AccountServiceClient accountServiceClient, BillServiceClient billServiceClient, RabbitTemplate rabbitTemplate) {
        this.depositRepository = depositRepository;
        this.accountServiceClient = accountServiceClient;
        this.billServiceClient = billServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account is null and bill is null");
        }

        if (billId != null) {
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = new BillRequestDTO();
            billRequestDTO.setAccountId(billResponseDTO.getAccountId());
            billRequestDTO.setCreationDate(billResponseDTO.getCreationDate());
            billRequestDTO.setIsDefault(billResponseDTO.getIsDefault());
            billRequestDTO.setOverdraftEnabled(billResponseDTO.getOverdraftEnabled());
            billRequestDTO.setAmount(billResponseDTO.getAmount().add(amount));
            billServiceClient.Update(billId, billRequestDTO);

            AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(billResponseDTO.getAccountId());
            depositRepository.save(new Deposit(amount, billId, OffsetDateTime.now(), accountResponseDTO.getEmail()));
            DepositResponseDTO depositResponseDTO = new DepositResponseDTO(amount, accountResponseDTO.getEmail());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, objectMapper
                        .writeValueAsString(depositResponseDTO));
            } catch (Exception e) {
                e.printStackTrace();
                throw new DepositServiceException("Can't send meassage to RabbitMQ");
            }
            return depositResponseDTO;
        }


        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequestDTO = createBillRequest(amount, defaultBill);
        billServiceClient.Update(defaultBill.getBillId(), billRequestDTO);
        AccountResponseDTO accountResponseDTO = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(amount, defaultBill.getBillId(), OffsetDateTime.now(), accountResponseDTO.getEmail()));
        return createResponse(amount,accountResponseDTO);

    }


    private BillRequestDTO createBillRequest(BigDecimal amount, BillResponseDTO billResponseDTO) {
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        billRequestDTO.setAccountId(billResponseDTO.getAccountId());
        billRequestDTO.setCreationDate(billResponseDTO.getCreationDate());
        billRequestDTO.setIsDefault(billResponseDTO.getIsDefault());
        billRequestDTO.setOverdraftEnabled(billResponseDTO.getOverdraftEnabled());
        billRequestDTO.setAmount(billResponseDTO.getAmount().add(amount));
        return billRequestDTO;
    }

    private BillResponseDTO getDefaultBill(Long accountId) {
        return billServiceClient.getBillByAccountId(accountId).stream()
                .filter(BillResponseDTO::getIsDefault).findAny().orElseThrow(() -> new DepositServiceException("Can't find default bill" + accountId));
    }

    private DepositResponseDTO createResponse(BigDecimal amount, AccountResponseDTO accountResponseDTO) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(amount, accountResponseDTO.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, objectMapper
                    .writeValueAsString(depositResponseDTO));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send meassage to RabbitMQ");
        }
        return depositResponseDTO;
    }
}
