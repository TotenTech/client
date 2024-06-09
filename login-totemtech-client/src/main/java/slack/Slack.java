package slack;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Slack {

    private static final HttpClient client = HttpClient.newHttpClient();

    public void sendSlackMessage(String url, JSONObject json) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(String.format("Status: %s", response.statusCode()));
//        System.out.println(String.format("Response: %s", response.body()));
    }
}