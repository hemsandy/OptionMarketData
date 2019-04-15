package com.wellsfargo.cmt.publisher;

import com.wellsfargo.cmt.model.Stock;
import com.wellsfargo.cmt.store.MarketDataStore;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by hems on 15/04/19.
 */
public class MarketDataPublisher implements Observer {



    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }



    public MarketDataPublisher() {
    }

    public void update(Observable observableObj, Object arg) {
        MarketDataStore store = (MarketDataStore)observableObj;
        Map<String, Stock> stockMap = store.getStockStoreMap();
        //Iterate and Publish to Queue/Topic


    }


    public void sendMessage(final Stock stock) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage(stock.toJSONString());
            }});
    }
}
