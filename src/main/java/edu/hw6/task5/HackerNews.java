package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String ROOT_ENDPOINT = "https://hacker-news.firebaseio.com/v0";
    private static final String TOP_STORIES_ENDPOINT = ROOT_ENDPOINT + "/topstories.json";
    private static final String ITEM_FORMAT_ENDPOINT = ROOT_ENDPOINT + "/item/%d.json";

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(TOP_STORIES_ENDPOINT))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            return new long[] {};
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return fromJson(response.body());
    }

    public static String news(long id) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(String.format(ITEM_FORMAT_ENDPOINT, id)))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            return null;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return null;
        }
        return extractTitleFromJson(response.body());
    }

    private static long[] fromJson(String json) {
        String noBrackets = json.substring(1, json.length() - 1);
        String[] nums = noBrackets.split(",");
        return Arrays.stream(nums)
            .map(Long::parseLong)
            .mapToLong(x -> x)
            .toArray();
    }

    private static String extractTitleFromJson(String json) {
        Pattern pattern = Pattern.compile("\"title\":\"(?<title>.*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group("title");
        }
        return null;
    }

}
