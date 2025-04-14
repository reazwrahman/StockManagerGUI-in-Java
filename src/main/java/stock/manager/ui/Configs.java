package stock.manager.ui;

public class Configs {
    public static final String STOCK_ENTRY_FILE_NAME = "StockEntries.json";
    public static final String COVER_PHOTO = "cover_photo_2.jpg";
    public static final API_MODE_ENUM API_MODE = API_MODE_ENUM.REMOTE;
    public static final String REMOTE_API_URL = "http://stock-manager.reaz-projects.uk";
    public static final String LOCAL_API_URL = "http://localhost:8080";
    public static final String ACCESS_KEY = "reaz_123";
    public static enum API_MODE_ENUM {
        LOCAL,
        REMOTE
    }
}
