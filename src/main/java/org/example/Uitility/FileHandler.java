package org.example.Uitility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.example.Configs;

public class FileHandler {

    public void writeToFile(String fileName, String content) {
        try {
            PrintStream oFile = new PrintStream(fileName);
            oFile.print(content);
            oFile.close();
        } catch (IOException ioe) {
            System.out.println("\n*** I/O Error ***\n" + ioe);
        }

    }

    public String readFromFile(String filename) throws IOException {
        String output = new String(Files.readAllBytes(Paths.get(filename)));
        return output;
    }

    public Map<String, Map<String, Float>> readStockEntries() {
        Map<String, Map<String, Double>> stockMap = new HashMap<>();
        try {
            String rawData = readFromFile(Configs.STOCK_ENTRY_FILE_NAME);
            Gson deserialized = new Gson();
            stockMap = deserialized.fromJson(rawData, Map.class);
            return castToFloat(stockMap);
        }catch (Exception e){
            System.out.println("FileHandler::readStockEntries exception occured" + e.toString());
            Map<String, Map<String, Float>> emptyMap = new HashMap<>();
            return emptyMap;
        }
    }

    private Map<String, Map<String, Float>> castToFloat(Map<String, Map<String, Double>> doubleMap){
        Map<String, Map<String, Float>> floatMap = new HashMap<>();
        for (String ticker: doubleMap.keySet()) {
            Float quantity = doubleMap.get(ticker).get("quantity").floatValue();
            Float cost = doubleMap.get(ticker).get("totalCost").floatValue();
            Map<String, Float> innerMap = new HashMap<>();
            innerMap.put("quantity", quantity);
            innerMap.put("totalCost", cost);
            floatMap.put(ticker, innerMap);
        }
        return floatMap;
    }
}
