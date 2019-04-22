package com.wellsfargo.cmt.publisher;

import com.wellsfargo.cmt.model.Stock;
import com.wellsfargo.cmt.store.MarketDataStore;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Abstract Class for
 * Market Data Publishing
 * Upon receiving the event from the Observable publishes
 *
 * Created by hems on 15/04/19.
 */
public abstract class MarketDataPublisher implements Observer {

    /**
     *
     * @param observableObj
     * @param arg
     */
    public void update(Observable observableObj, Object arg) {
        //System.out.println("received event..");
        MarketDataStore store = (MarketDataStore)observableObj;
        Map<String, Stock> stockMap = store.getStockStoreMap();
        //Iterate and Publish to Queue/Topic
        Iterator<Stock> stockIterator = stockMap.values().iterator();
        while(stockIterator.hasNext()) {
            Stock stockTmp = stockIterator.next();
            publish(stockTmp);
        }
    }

    protected abstract void publish(Stock stock);


}
