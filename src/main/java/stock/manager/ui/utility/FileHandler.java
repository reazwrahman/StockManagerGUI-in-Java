package stock.manager.ui.utility;

import com.google.gson.Gson;
import stock.manager.ui.Configs;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Map<String, String>> readStockEntries() {
        Map<String, Map<String, String>> stockMap = new HashMap<>();
        try {
            String rawData = readFromFile(Configs.STOCK_ENTRY_FILE_NAME);
            Gson deserialized = new Gson();
            stockMap = deserialized.fromJson(rawData, Map.class);
            return stockMap;
        } catch (Exception e) {
            System.out.println("FileHandler::readStockEntries exception occured" + e);
            return stockMap;
        }
    }
}
