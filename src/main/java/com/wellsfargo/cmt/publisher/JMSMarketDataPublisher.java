package com.wellsfargo.cmt.publisher;

import com.wellsfargo.cmt.model.Stock;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * JMS Market Data Publisher publishes
 * stock information to JMS Queue/Topic
 * using the provided jmsTemplate
 * Each message contains a Stock information in a JSON String
 * has expiration time of 5 minutes
 *
 *
 * Created by hems on 16/04/19.
 */
public class JMSMarketDataPublisher extends MarketDataPublisher{

    protected JmsTemplate jmsTemplate;
    protected Destination destination;

    public JMSMarketDataPublisher(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }
    /**
     * Sends Stock information in JSON string format to
     * <code>destination</code>, sets expiration time of
     * the message to 5 minutes
     * @param stock
     */
    public void publish(final Stock stock) {
        jmsTemplate.setTimeToLive(1000 * 60 * 5);//5 minutes life
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(stock.toJSONString());
            }});
    }
}
