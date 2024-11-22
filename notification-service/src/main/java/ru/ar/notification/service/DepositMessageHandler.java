package ru.ar.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.ar.notification.config.RabbitMQConfig;

@Service
public class DepositMessageHandler {
    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper mapper = new ObjectMapper();
        DepositResponseDTO depositResponseDTO = mapper.readValue(jsonBody, DepositResponseDTO.class);
        System.out.println(depositResponseDTO);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(depositResponseDTO.getMail());
        simpleMailMessage.setFrom("Alex@Gaasd.ru");
        simpleMailMessage.setSubject("Deposit Notification");
        simpleMailMessage.setText("Make deposit, sum:" + depositResponseDTO.getAmount());

        try {
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e) {
            System.out.println(e);;
        }
    }
}
