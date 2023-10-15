package com.project.client;

import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class CallHttpClient {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final HttpClient httpClient = HttpClient.newBuilder().executor(executorService).version(HttpClient.Version.HTTP_1_1).build();

    public JsonObject callHttpWithUrl(String url, String method, JsonObject payload) {
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder();
            request.uri(URI.create(url));
            if (payload != null && !payload.isEmpty()) {
                request.method(method, HttpRequest.BodyPublishers.ofString(payload.encode()));
                Log.warnf("===Request: Call http to URL: [%s], Method: [%s] with body %s", url, method, payload);
            } else {
                request.method(method, HttpRequest.BodyPublishers.ofString(""));
                Log.warnf("===Request: Call http to URL: [%s], Method: [%s]", url, method);
            }
            JsonObject response = new JsonObject(this.httpClient.send(HttpRequest.newBuilder()
                    .GET().timeout(Duration.ofSeconds(30))
                    .uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString()).body());
            Log.infof("===RESPONSE: URL: [%s], data %s", url, response);
            return response;
        } catch (Exception exc) {
            Log.error("Call http to url [%s] with error [%s]", url, exc.getCause());
            return null;
        }
    }
}
