package stock.manager.ui.stock_manager;

import stock.manager.ui.utility.FileHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class StockSorter {
    private final FileHandler m_fileHandler;
    private ArrayList<Stock> m_sortedData;

    public StockSorter() {
        m_fileHandler = new FileHandler();
    }

    private void loadData() {
        m_sortedData = new ArrayList<>();
        Map<String, Map<String, String>> rawData = m_fileHandler.readStockEntries();
        for (String ticker : rawData.keySet()) {
            Float quantity = Float.parseFloat(rawData.get(ticker).get("quantity"));
            Float totalCost = Float.parseFloat(rawData.get(ticker).get("totalCost"));
            Stock stock = new Stock(ticker, quantity, totalCost);
            m_sortedData.add(stock);
        }

        Collections.sort(m_sortedData);
    }

    public void updateData() {
        loadData();
    }

    public ArrayList<Stock> getSortedData() {
        return new ArrayList<>(m_sortedData);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Title block
        builder.append(String.format("Stock Data (Sorted by %s)\n", Stock.m_comparator));
        builder.append("=================================================\n");

        // Header row
        builder.append("\n");
        builder.append(String.format("%-4s %-15s %-15s %-15s\n", " ", "Ticker", "Quantity", "TotalCost"));

        // Separator
        builder.append("=================================================\n");

        // Data rows
        for (int i = m_sortedData.size() - 1; i >= 0; i--) {
            Stock stock = m_sortedData.get(i);
            builder.append(String.format("%-4d %-15s %-15s %-15s\n",
                    (m_sortedData.size() - i),
                    stock.m_ticker,
                    String.format("%.2f", stock.m_quantity),
                    String.format("%.2f", stock.m_totalCost)));
        }

        return builder.toString();
    }

}
