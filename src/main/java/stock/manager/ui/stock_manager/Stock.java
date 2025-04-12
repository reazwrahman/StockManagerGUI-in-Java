package stock.manager.ui.stock_manager;


public class Stock implements Comparable<Stock> {
    public static String m_comparator = "TotalCost";
    public String m_ticker;
    public Float m_quantity;
    public Float m_totalCost;

    public Stock(String ticker, Float quantity, Float totalCost) {
        m_ticker = ticker;
        m_quantity = quantity;
        m_totalCost = totalCost;
    }


    public int compareTo(Stock otherStock) {
        return this.m_totalCost.compareTo(otherStock.m_totalCost);
    }
}
