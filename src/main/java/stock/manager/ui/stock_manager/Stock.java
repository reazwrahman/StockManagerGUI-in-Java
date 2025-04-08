package stock.manager.ui.stock_manager;

public class Stock implements Comparable<Stock> {
    public static String m_comparator = "TotalCost";
    public String m_ticker;
    public Float m_quantity;
    public Float m_totalCost;

    public Stock(String ticker, Float quantiy, Float totalCost) {
        m_ticker = ticker;
        m_quantity = quantiy;
        m_totalCost = totalCost;
    }


    public int compareTo(Stock otherStock) {
        return Float.compare(this.m_totalCost, otherStock.m_totalCost);
    }
}
