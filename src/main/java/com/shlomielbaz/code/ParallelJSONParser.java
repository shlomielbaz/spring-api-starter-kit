package com.shlomielbaz.code;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelJSONParser {
    final static Gson gson = new GsonBuilder().create();

    public static void main(String[] args) {
        // URLs of JSON endpoints to fetch
        String[] urls = {
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://jsonplaceholder.typicode.com/posts/2",
                "https://jsonplaceholder.typicode.com/posts/3"
        };

        // Create an ExecutorService with a fixed pool of threads
        ExecutorService executorService = Executors.newFixedThreadPool(urls.length);

        // List to hold Future objects representing the asynchronous tasks
        List<Future<Post>> futures = new ArrayList<>();

        // Submit tasks to fetch JSON from each URL in parallel
        for (String url : urls) {
            Future<Post> future = executorService.submit(() -> fetchJsonAndParse(url));
            futures.add(future);
        }

        // Parse JSON responses from all tasks in parallel
        for (Future<Post> future : futures) {
            try {
                Post post = future.get();
                System.out.println("Post ID: " + post.getId());
                System.out.println("Post Title: " + post.getTitle());
                System.out.println("Post Body: " + post.getBody());
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Shutdown the ExecutorService
        executorService.shutdown();
    }

    private static Post fetchJsonAndParse(String urlString) throws IOException {
        // Create a URL object from the string
        URL url = new URL(urlString);

        // Open an HttpURLConnection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the JSON response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        // Parse JSON into a Post object using Gson
        //Gson gson = new Gson();
        Post post = gson.fromJson(responseBuilder.toString(), Post.class);

        // Close the connection
        connection.disconnect();

        return post;
    }
}

// Define a class to represent the structure of a post JSON object
class Post {
    private int id;
    private String title;
    private String body;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
