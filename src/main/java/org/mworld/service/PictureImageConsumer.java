package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.mworld.pojo.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureImageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PictureImageConsumer.class);

    @RabbitListener(queues = "q.picture.image")
    public void listen(String message) {
        try {
            Picture picture = new ObjectMapper().readValue(message, Picture.class);
            logger.info("Consuming jpg/png image : {}", picture.toString());
        } catch (Exception e) {
            logger.error("Exception while reading picture image : ", e);
        }
    }

    /*
    @RabbitListener(queues = "q.mypicture.image")
    public void listen2(String message) throws Exception {
        Picture picture = new ObjectMapper().readValue(message, Picture.class);
        if (picture.getSize() > 9000)
            throw new AmqpRejectAndDontRequeueException("Pic is too large : "+picture.getName() + ", " +picture.getSize());
        logger.info("Consuming jpg/png image : {}", picture);
    }
    */

    // Example to showcase acknowledgment - instead of above example where we directly throw exception
    // Now we'll handle consumer rejection (above method - listen2)
    @RabbitListener(queues = "q.mypicture.image")
    public void listen3(Message message, Channel channel) throws Exception {
        Picture picture = new ObjectMapper().readValue(message.getBody(), Picture.class);
        if (picture.getSize() > 9000)
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        logger.info("Consuming image : {}", picture);
        // Inform RabbitMq that our process is done
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
