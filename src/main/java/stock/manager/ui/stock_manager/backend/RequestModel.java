package stock.manager.ui.stock_manager.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import stock.manager.ui.stock_manager.Stock;

import java.util.ArrayList;
import java.util.List;


class StockStringified {
    public String ticker;
    public String quantity;
    public String totalCost;

    public StockStringified(String ticker, String quantity, String totalCost) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }
}

public class RequestModel {

    public String accessKey;
    public List<StockStringified> stocks;

    public RequestModel(String key, List<Stock> stocks) {
        this.accessKey = key;
        this.stocks = convertToString(stocks);
    }

    public static String serialize(RequestModel request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }

    private List<StockStringified> convertToString(List<Stock> stocks) {
        List<StockStringified> result = new ArrayList<>();
        for (Stock stock : stocks) {
            result.add(new StockStringified(stock.m_ticker, Float.toString(stock.m_quantity),
                    Float.toString(stock.m_totalCost)));
        }
        return result;
    }
}

