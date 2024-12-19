package org.example.StockManager;

public class Stock implements Comparable<Stock>{
    public String m_ticker;
    public Float m_quantity;
    public Float m_totalCost;

    public static String m_comparator = "TotalCost";

    public Stock(String ticker, Float quantiy, Float totalCost){
        m_ticker = ticker;
        m_quantity = quantiy;
        m_totalCost = totalCost;
    }


    public int compareTo(Stock otherStock)
    {
        return Float.compare(this.m_totalCost, otherStock.m_totalCost);
    }
}
