package com.wellsfargo.cmt.publisher;

import com.wellsfargo.cmt.model.Stock;
import com.wellsfargo.cmt.store.MarketDataStore;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Market Data Publisher
 * Upon receiving the event from the Observable publishes
 * stock information to JMS Queue/Topic
 *
 * using the provided jmsTemplate
 * Each message contains a Stock information in a JSON String
 * has expiration time of 5 minutes
 *
 *
 * Created by hems on 15/04/19.
 */
public class MarketDataPublisher implements Observer {

    private JmsTemplate jmsTemplate;
    private Destination destination;

    public MarketDataPublisher(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    /**
     *
     * @param observableObj
     * @param arg
     */
    public void update(Observable observableObj, Object arg) {
        MarketDataStore store = (MarketDataStore)observableObj;
        Map<String, Stock> stockMap = store.getStockStoreMap();
        //Iterate and Publish to Queue/Topic
        Iterator<Stock> stockIterator = stockMap.values().iterator();
        while(stockIterator.hasNext()) {
            Stock stockTmp = stockIterator.next();
            sendMessage(stockTmp);
        }
    }


    /**
     * Sends Stock information in JSON string format to
     * <code>destination</code>, sets expiration time of
     * the message to 5 minutes
     * @param stock
     */
    public void sendMessage(final Stock stock) {
        jmsTemplate.setTimeToLive(1000 * 60 * 5);//5 minutes life
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(stock.toJSONString());
            }});
    }
}
