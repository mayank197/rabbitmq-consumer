package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);

    @RabbitListener(queues = "course.employee")
    public void listen(String message) {
        try {
            Employee employee = objectMapper.readValue(message, Employee.class);
            logger.info("Data : {}", employee.toString());
        } catch (Exception e) {
            logger.error("Exception while reading employee consumer data : ", e);
        }

    }

}
