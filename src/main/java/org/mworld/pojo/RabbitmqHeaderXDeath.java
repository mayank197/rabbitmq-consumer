package org.mworld.pojo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RabbitmqHeaderXDeath {

    private int count;
    private String exchange;
    private String queue;
    private String reason;
    private List<String> routingKeys;
    private Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RabbitmqHeaderXDeath that = (RabbitmqHeaderXDeath) o;
        return count == that.count && Objects.equals(exchange, that.exchange) && Objects.equals(queue, that.queue) && Objects.equals(reason, that.reason) && Objects.equals(routingKeys, that.routingKeys) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, exchange, queue, reason, routingKeys, time);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getRoutingKeys() {
        return routingKeys;
    }

    public void setRoutingKeys(List<String> routingKeys) {
        this.routingKeys = routingKeys;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
