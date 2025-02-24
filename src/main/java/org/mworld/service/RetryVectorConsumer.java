package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.mworld.pojo.Picture;
import org.mworld.utils.DlxErrorProcessingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RetryVectorConsumer {

    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";
    private DlxErrorProcessingHandler dlxErrorProcessingHandler;
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(RetryVectorConsumer.class);

    public RetryVectorConsumer() {
        this.objectMapper = new ObjectMapper();
        this.dlxErrorProcessingHandler = new DlxErrorProcessingHandler(DEAD_EXCHANGE_NAME);
    }

    @RabbitListener(queues = "q.guideline.vector.work")
    public void listen(Message message, Channel channel) {
        try {
            Picture p = objectMapper.readValue(message.getBody(), Picture.class);
            if (p.getSize() > 9000)
                throw new IOException("Size too large");
            else {
                logger.info("Creating thumbnail and publishing {}", p);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (IOException e) {
            logger.warn("Error in consuming vector image : {} : {}", new String(message.getBody()), e.getMessage());
            dlxErrorProcessingHandler.handleErrorProcessingMessage(message, channel);
        }
    }

}
