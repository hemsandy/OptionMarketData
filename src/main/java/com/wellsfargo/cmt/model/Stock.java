package com.wellsfargo.cmt.model;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by hems on 13/04/19.
 */
public class Stock {

    public Stock(String string, double d) {
        this.symbol = string;
        this.price = d;
    }

    public String symbol;
    public double price;
    public double delta;
    public LocalDateTime lastUpdate;

    public static DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    public void update(double delta2) {
        this.delta = delta2;
        price = price + delta2;
        lastUpdate=LocalDateTime.now();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stock [symbol=")
                .append(symbol).append(", price=")
                .append(price).append(", delta=")
                .append(delta).append(", lastUpdate=")
                .append(lastUpdate).append("]");
        return builder.toString();
    }

    public String toJSONString() {
        JsonObject result = Json.createObjectBuilder()
                .add("symbol", symbol)
                .add("price", price)
                .add("delta", delta)
                .add("lastUpdate",lastUpdate.format(formatter))
                .build();

        return result.toString();
    }
    public static Stock fromJsonString(String jsonObjectStr) {
        Stock stock = null;
        try {

            JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
            JsonObject object = jsonReader.readObject();
            jsonReader.close();

            String smb = object.getString("symbol");
            double pric = object.getJsonNumber("price").doubleValue();
            double delta = object.getJsonNumber("delta").doubleValue();
            LocalDateTime dateTime = LocalDateTime.parse(object.getString("lastUpdate"), formatter);
            stock = new Stock(smb,pric);
            stock.delta = delta;
            stock.lastUpdate = dateTime;
        }catch(Exception e) {
            System.out.println("Parse Exception :" + e.getMessage() + "forMessage:" + jsonObjectStr);
        }

        return stock;
    }
}
