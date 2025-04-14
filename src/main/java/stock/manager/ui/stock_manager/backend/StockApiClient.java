package stock.manager.ui.stock_manager.backend;

import stock.manager.ui.Configs;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class StockApiClient {

    private static final String BASE_URL = Configs.API_MODE == Configs.API_MODE_ENUM.REMOTE ?
                                            Configs.REMOTE_API_URL : Configs.LOCAL_API_URL;
    private final HttpClient client;

    public StockApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    public ResponseModel sortByReturnRate(RequestModel request) throws IOException, InterruptedException {
        return postJson("/sort-by-return-rate", request);
    }

    public ResponseModel sortByTotalGain(RequestModel request) throws IOException, InterruptedException {
        return postJson("/sort-by-total-gain", request);
    }

    private ResponseModel postJson(String path, RequestModel request) throws IOException, InterruptedException {
        String requestBody = RequestModel.serialize(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return ResponseModel.deserialize(response.body());
    }
}