package org.mworld.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class FixedRateConsumer {

    private final static Logger logger = LoggerFactory.getLogger(FixedRateConsumer.class);

    @RabbitListener(queues = "course.fixedRate", concurrency = "3")
    public void listen(String message) throws InterruptedException {
        logger.info("Consuming {} on Thread {}", message, Thread.currentThread().getName());
        Thread.sleep(ThreadLocalRandom.current().nextLong(2000));
    }

}
