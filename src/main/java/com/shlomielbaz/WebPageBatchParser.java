package com.shlomielbaz;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebPageBatchParser {

    // Fetches and parses a single web page, returning the page title
    private static String fetchPageTitle(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
            Document doc = Jsoup.parse(connection.getInputStream(), "UTF-8", "");
            return doc.title(); // As an example, we're extracting the page title
        } else {
            throw new IOException("Failed to fetch page with response code: " + connection.getResponseCode());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // List of URLs to parse (add more URLs as needed)
        List<String> urls = List.of(
                "https://www.example.com",
                "https://www.wikipedia.org",
                "https://www.github.com",
                "https://www.stackoverflow.com",
                "https://www.reddit.com"
        );

        // Shared list to store the parsed results
        List<String> parsedResults = Collections.synchronizedList(new ArrayList<>());

        // Number of parallel threads
        int numThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Define batch size
        int batchSize = 2; // Adjust based on resource capacity and desired parallelism

        // Process the URLs in batches
        for (int i = 0; i < urls.size(); i += batchSize) {
            // Get the current batch
            List<String> batch = urls.subList(i, Math.min(i + batchSize, urls.size()));

//            // Create a list of CompletableFutures for the batch
//            List<CompletableFuture<Void>> futures = batch.stream().map(url -> CompletableFuture.runAsync(() -> {
//                try {
//                    String title = fetchPageTitle(url);
//                    synchronized (parsedResults) {
//                        parsedResults.add(title);
//                    }
//                } catch (IOException e) {
//                    System.err.println("Error fetching web page: " + url + ", " + e.getMessage());
//                }
//            }, executor)).collect(Collectors.toList());
//            // Wait for the batch to complete
//            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Create a list of CompletableFutures for the batch
            CompletableFuture.allOf(batch.stream().map(url -> CompletableFuture.runAsync(() -> {
                try {
                    String title = fetchPageTitle(url);
                    synchronized (parsedResults) {
                        parsedResults.add(title);
                    }
                } catch (IOException e) {
                    System.err.println("Error fetching web page: " + url + ", " + e.getMessage());
                }
            }, executor)).toArray(CompletableFuture[]::new)).join();
        }

        // Shutdown the executor
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Print the parsed results
        System.out.println("Parsed results:");
        for (String result : parsedResults) {
            System.out.println(result);
        }
    }
}
