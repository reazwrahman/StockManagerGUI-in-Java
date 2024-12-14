package org.example.Uitility;

import com.google.gson.Gson;
import org.example.Configs;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public Map<String, Map<String, Float>> readStockEntries() throws IOException {
        String rawData = readFromFile(new Configs().STOCK_ENTRY_FILE_NAME);
        Gson deserialized = new Gson();
        Map<String, Map<String, Float>> map = deserialized.fromJson(rawData, Map.class);
        return map;
    }
}
