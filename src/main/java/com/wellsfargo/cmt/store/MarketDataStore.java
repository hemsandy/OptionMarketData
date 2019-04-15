package com.wellsfargo.cmt.store;

import com.wellsfargo.cmt.model.Stock;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hems on 15/04/19.
 */
public class MarketDataStore extends Observable implements Runnable{

    long delay;

    Map<String, Stock> stockStoreMap = new ConcurrentHashMap<String, Stock>();

    public MarketDataStore(long delaySeconds) {
        this.delay = delaySeconds;
    }


    private void updateStocks() {

    }

    public void run() {
        while(true) {

            updateStocks();
            setChanged();
            notifyObservers();
            try { Thread.sleep(delay); }
            catch(InterruptedException ie) {}
        }
    }

    public int addStock(Stock stock) {
        stockStoreMap.put(stock.symbol, stock);
        return  stockStoreMap.size();
    }

    public Map<String, Stock> getStockStoreMap() {
        return stockStoreMap;
    }
}
