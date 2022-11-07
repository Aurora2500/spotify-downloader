package es.ulpgc.spotify.downloader;


import io.reactivex.rxjava3.core.Observable;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.nio.charset.StandardCharsets.UTF_8;

public class SpotifyAccessor {
    private final static String API_BASE_URI = "https://api.spotify.com/v1";
    private static SpotifyAccessor instance = null;

    private final HttpClient client;
    private SpotifyAuthorization.Token authorization;

    private int request_count = 0;

    private SpotifyAccessor() throws Exception {
        client = HttpClient.newHttpClient();
        authorization = SpotifyAuthorization.get();
    }

    public static SpotifyAccessor getInstance() throws Exception {
        if (instance == null) {
            instance = new SpotifyAccessor();
        }
        return instance;
    }

    private void checkAuthorization() throws Exception {
        if (authorization.isValid()) return;
        System.out.println("Token has expired. Requesting other...");
        authorization = SpotifyAuthorization.get();
    }

    public int count() {
        return request_count;
    }

    public Observable<String> get(String path, Map<String, String> params) throws Exception {
        request_count += 1;
        return responseOf(request(path, params).GET().build());
    }

    private HttpRequest.Builder request(String path, Map<String, String> queryParams) throws Exception {
        checkAuthorization();
        System.out.println("Requesting " + path + with(queryParams));
        return HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URI + path + with(queryParams)))
                .header("Authorization", authorization.token_type + " " + authorization.access_token)
                .header("Content-Type", "application/json");
    }

    private String with(Map<String, String> queryParams) {
        if (queryParams.isEmpty()) return "";
        StringJoiner result = new StringJoiner("&");
        for (Map.Entry<String, String> entry : queryParams.entrySet())
            result.add(encode(entry));
        return "?" + result;
    }

    private String encode(Map.Entry<String, String> entry) {
        return String.format("%s=%s",
                URLEncoder.encode(entry.getKey(), UTF_8),
                URLEncoder.encode(entry.getValue(), UTF_8)
        );
    }

    private Observable<String> responseOf(HttpRequest request) throws Exception {
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return Observable.fromFuture(response)
            .flatMap(r -> {
                if (r.statusCode() == HTTP_OK) {
                    return Observable.just(r.body());
                } else if (r.statusCode() == 429) {
                    int retryAfter = Integer.parseInt(r.headers().firstValue("Retry-After").orElse("0"));
                    return Observable.timer(retryAfter, TimeUnit.SECONDS).flatMap(x -> responseOf(request));
                } else {
                    return Observable.error(new Exception("Request failed with status code " + r.statusCode()));
                }
            });
    }
}
