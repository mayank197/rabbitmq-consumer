package org.mworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mworld.pojo.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PictureMobileConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PictureMobileConsumer.class);

    @RabbitListener(queues = "q.picture.mobile")
    public void listen(String message) {
        try {
            Picture picture = new ObjectMapper().readValue(message, Picture.class);
            logger.info("Consuming mobile image : {}", picture.toString());
        } catch (Exception e) {
            logger.error("Exception while reading mobile image : ", e);
        }
    }

}
