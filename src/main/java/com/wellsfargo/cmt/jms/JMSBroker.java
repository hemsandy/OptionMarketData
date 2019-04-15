package com.wellsfargo.cmt.jms;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class JMSBroker {

    private String host;
    private String port;

    public JMSBroker(String host, String port) {
        this.host=host;
        this.port=port;
    }


    public void startBroker() throws Exception {
        BrokerService broker = BrokerFactory.createBroker(new URI("broker:(tcp://"+host+":"+port+")"));
        broker.start();
    }
}