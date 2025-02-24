package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AccountingConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(AccountingConsumer.class);

    @RabbitListener(queues = "q.hr.accounting")
    public void listen(String message) {
        try {
            Employee employee = objectMapper.readValue(message, Employee.class);
            logger.info("In Accounting, Data : {}", employee.toString());
        } catch (Exception e) {
            logger.error("Exception while reading employee data : ", e);
        }
    }

}
