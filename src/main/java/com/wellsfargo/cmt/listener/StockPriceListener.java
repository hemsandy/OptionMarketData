package com.wellsfargo.cmt.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.awt.font.TextMeasurer;

/**
 * Created by hems on 17/04/19.
 */
public class StockPriceListener implements MessageListener{

    public void onMessage(Message message) {
        try{
            TextMessage textMessage = (TextMessage)message;
            String payLoad = textMessage.getText();
            System.out.println("Stock: " + payLoad);
            message.acknowledge();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
