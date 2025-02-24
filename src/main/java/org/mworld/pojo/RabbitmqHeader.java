package org.mworld.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RabbitmqHeader {

    private List<RabbitmqHeaderXDeath> xDeaths = new ArrayList<>(2);
    private String xFirstDeathExchange = "";
    private String xFirstDeathQueue = "";
    private String xFirstDeathReason = "";

    public void setxFirstDeathExchange(String xFirstDeathExchange) {
        this.xFirstDeathExchange = xFirstDeathExchange;
    }

    public void setxFirstDeathQueue(String xFirstDeathQueue) {
        this.xFirstDeathQueue = xFirstDeathQueue;
    }

    public void setxFirstDeathReason(String xFirstDeathReason) {
        this.xFirstDeathReason = xFirstDeathReason;
    }

    public RabbitmqHeader(Map<String, Object> headers) {
        if (headers != null) {
            this.setxFirstDeathExchange(headers.get("x-first-death-exchange").toString());
            this.setxFirstDeathQueue(headers.get("x-first-death-queue").toString());
            this.setxFirstDeathReason(headers.get("x-first-death-reason").toString());

            List<Map<String, Object>> xDeathHeaders = (List<Map<String, Object>>) headers.get("x-death");

            if (xDeathHeaders != null) {
                for(Map<String, Object> x : xDeathHeaders) {
                    RabbitmqHeaderXDeath headerXDeath = new RabbitmqHeaderXDeath();
                    headerXDeath.setReason(x.get("reason").toString());
                    headerXDeath.setCount(Integer.parseInt(x.get("count").toString()));
                    headerXDeath.setExchange(x.get("exchange").toString());
                    headerXDeath.setQueue(x.get("queue").toString());
                    headerXDeath.setRoutingKeys((List<String> )x.get("routing-keys"));
                    headerXDeath.setTime((Date) x.get("time"));
                    xDeaths.add(headerXDeath);
                }
            }

        }
    }

    public int getFailedRetryCount() {
        for(RabbitmqHeaderXDeath xDeath: xDeaths) {
            String exchangeName = xDeath.getExchange();
            String queueName = xDeath.getQueue();
            if (exchangeName.toLowerCase().endsWith("wait") && queueName.toLowerCase().endsWith("wait")) {
                return xDeath.getCount();
            }
        }
        return 0;
    }
}
