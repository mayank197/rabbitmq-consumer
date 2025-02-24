package org.mworld.service;

import org.mworld.pojo.DummyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AnotherDummyConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AnotherDummyConsumer.class);

    // will automatically create the queue, exchange and bind them
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "q.auto-dummy", durable = "true"),
            exchange = @Exchange(name = "x.auto-dummy"),
            key = "routing-key",
            ignoreDeclarationExceptions = "true"
    ))
    public void listenDummy(DummyMessage message) {
        logger.info("Consuming dummy message {}", message);
    }

}
