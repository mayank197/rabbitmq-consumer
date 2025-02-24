package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureLogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PictureLogConsumer.class);

    @RabbitListener(queues = "q.picture.log")
    public void listen(String message) {
        try {
            Picture picture = new ObjectMapper().readValue(message, Picture.class);
            logger.info("Consuming log image : {}", picture.toString());
        } catch (Exception e) {
            logger.error("Exception while reading log image : ", e);
        }
    }

}
