package com.wellsfargo.cmt.store;

import com.wellsfargo.cmt.model.Stock;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Market Data Store
 * Holds the Stock information and regularly (every minute)
 * updates stock (randomly ) and informs the <code>observers</code>
 * of Store.
 * Created by hems on 15/04/19.
 */
public class MarketDataStore extends Observable implements Runnable{

    long delay;

    Map<String, Stock> stockStoreMap = new ConcurrentHashMap<String, Stock>();

    double[] updateValues = {0.01, 0.02, -0.01, -0.02, 0.03, -0.03, 0.00,  0.04, -0.04, 0.1};
    Random random = new Random(10);

    public MarketDataStore(long delaySeconds) {
        this.delay = delaySeconds;
    }

    /**
     * Iterates thru the map of Stocks and updates
     * each stock price using the randomizer.
     */
    private void updateStocks() {
        Iterator<Stock> iteratorOfStocks = stockStoreMap.values().iterator();
        while(iteratorOfStocks.hasNext()) {
            Stock tmpStock = randomUpdateStockValue(iteratorOfStocks.next());
        }
    }

    /**
     * Updates the stock price with delta of
     * <code>updateValues</code> selected randomly
     * @param stock
     * @return
     */
    private Stock randomUpdateStockValue(Stock stock) {
        stock.update(updateValues[random.nextInt(9)]);
        return stock;
    }

    /**
     * runs as a seperate thread
     * and invokes the <code>updateStocks</code> and notifies the
     * Observers for each <code>delay</code> ms
     */
    public void run() {
        while(true) {
            updateStocks();
            setChanged();
            notifyObservers();
            try { Thread.sleep(delay); }
            catch(InterruptedException ie) {}
        }
    }

    /**
     * Adds a Stock to the Store
     * @param stock
     * @return
     */
    public int addStock(Stock stock) {
        stockStoreMap.put(stock.symbol, stock);
        return  stockStoreMap.size();
    }

    /**
     * Returns the Store
     * @return
     */
    public Map<String, Stock> getStockStoreMap() {
        return stockStoreMap;
    }
}
