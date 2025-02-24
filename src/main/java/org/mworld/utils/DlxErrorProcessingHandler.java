package org.mworld.utils;

import com.rabbitmq.client.Channel;
import org.mworld.pojo.RabbitmqHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;

import java.util.Date;

public class DlxErrorProcessingHandler {

    private static final Logger logger = LoggerFactory.getLogger(DlxErrorProcessingHandler.class);

    @NonNull
    private String deadExchangeName;
    private int maxRetryCount = 3;

    public DlxErrorProcessingHandler(@NonNull String deadExchangeName) {
        this.deadExchangeName = deadExchangeName;
    }

    public DlxErrorProcessingHandler(@NonNull String deadExchangeName, int maxRetryCount) {
        this.deadExchangeName = deadExchangeName;
        this.setMaxRetryCount(maxRetryCount);
    }

    public void setMaxRetryCount(int maxRetryCount) throws IllegalArgumentException {
        if (maxRetryCount > 1000)
            throw new IllegalArgumentException("Max retry must be between 0-1000");
        this.maxRetryCount = maxRetryCount;
    }

    public boolean handleErrorProcessingMessage(Message message, Channel channel) {
        RabbitmqHeader rabbitmqHeader = new RabbitmqHeader(message.getMessageProperties().getHeaders());
        try {
            if (rabbitmqHeader.getFailedRetryCount() >= maxRetryCount) {
                logger.warn("[DEAD] Error at " + new Date() + " on retry " + rabbitmqHeader.getFailedRetryCount() + " for message " +message);
                channel.basicPublish(deadExchangeName, message.getMessageProperties().getReceivedRoutingKey(), null, message.getBody());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                logger.debug("[REQUEUE] at " + new Date() + " for message " +message);
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
            return true;
        } catch (Exception e) {
            logger.warn("[HANDLER-FAILED] for message "+message);
        }
        return false;
    }

}
