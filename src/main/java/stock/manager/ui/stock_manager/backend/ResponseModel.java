package stock.manager.ui.stock_manager.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import stock.manager.ui.stock_manager.StockWithPrice;

import java.util.List;


public class ResponseModel {
    public List<StockWithPrice> stocks;

    public static ResponseModel deserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ResponseModel.class);
    }
}

