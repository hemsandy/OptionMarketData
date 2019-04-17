package com.wellsfargo.cmt;

import com.wellsfargo.cmt.jms.JMSBroker;
import com.wellsfargo.cmt.store.MarketDataStore;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * Created by hems on 17/04/19.
 */
public class MarketDataServiceMain {

    public static void main(String[] args) {
        if(args.length == 1 ) {
            String service = args[0];
            if("BOTH".equalsIgnoreCase(service) || "BROKER".equalsIgnoreCase(service)) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/broker-application-context.xml");
                JMSBroker broker = (JMSBroker) applicationContext.getBean("jmsBroker");
                try {
                    broker.startBroker();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if("BOTH".equalsIgnoreCase(service) || "PUBLISHER".equalsIgnoreCase(service)) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/publisher-application-context.xml");
                MarketDataStore marketDataStore = (MarketDataStore) applicationContext.getBean("marketDataStore");
                Thread storeStart = new Thread(marketDataStore);
                storeStart.start();
            }
            if("SUBSCRIBER".equalsIgnoreCase(service)) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/subscriber-application-context.xml");

            }

        }else{
            System.out.println("Please Provide Input Arguments: [BROKER/PUBLISHER/BOTH]");
        }
    }
}
