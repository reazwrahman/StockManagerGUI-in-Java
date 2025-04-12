package stock.manager.ui.stock_manager.backend;

import stock.manager.ui.stock_manager.StockSorter;
import stock.manager.ui.stock_manager.StockWithPrice;

import java.util.List;

public class DisplayHelper {
    private final String ACCESS_KEY = "reaz_123";
    private final String ALTERNATE_MESSAGE = "Something went wrong downstream, please try again later.";
    private StockApiClient m_backend;
    private StockSorter m_stockSorter;

    public DisplayHelper() {
        m_backend = new StockApiClient();
        m_stockSorter = new StockSorter();
        m_stockSorter.loadData();
    }

    public String getReturnRateString() {
        m_stockSorter.loadData();
        try {
            RequestModel request = new RequestModel(ACCESS_KEY, m_stockSorter.getSortedData());
            ResponseModel response = m_backend.sortByReturnRate(request);
            return toString(response.stocks, "Return Rate");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ALTERNATE_MESSAGE;
        }
    }

    public String getTotalGainString() {
        m_stockSorter.loadData();
        try {
            RequestModel request = new RequestModel(ACCESS_KEY, m_stockSorter.getSortedData());
            ResponseModel response = m_backend.sortByTotalGain(request);
            return toString(response.stocks, "Net Gain");

        } catch (Exception e) {
            return ALTERNATE_MESSAGE;
        }
    }

    String toString(List<StockWithPrice> stocks, String comparator) {
        StringBuilder builder = new StringBuilder();

        // Title block
        builder.append(String.format("Stock Data (Sorted by %s)\n", comparator));
        // builder.append("=========================================================================\n");

        // Header row
        builder.append("\n");
        builder.append(String.format("%-4s %-15s %-15s %-15s %-15s %-15s %-15s\n", " ", "Ticker", "Quantity", "TotalCost",
                "Price", "TotalGain", "ReturnRate"));

        // Separator
        builder.append("==================================================================================================\n");

        // Data rows
        for (int i = 0; i < stocks.size(); i++) {
            StockWithPrice stock = stocks.get(i);
            builder.append(String.format("%-4d %-15s %-15s %-15s %-15s %-15s %-15s\n",
                    (i + 1),
                    stock.ticker,
                    String.format("%.2f", stock.quantity),
                    String.format("%.2f", stock.totalCost),
                    String.format("%.2f", stock.price),
                    String.format("%.2f", stock.totalGain),
                    String.format("%.2f", stock.returnRate)
            ));
        }

        return builder.toString();
    }
}
