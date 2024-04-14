package com.shlomielbaz.code;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

public class ParallelPageParser {
    public static void main(String[] args) {

        String[] urls = {"https://dummyjson.com/products/?limit=10", "https://dummyjson.com/products/?limit=10&skip=10", "https://dummyjson.com/products/?limit=10&skip=20"};

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(urls.length);

        // List to hold Future objects representing the JSON parsing tasks
        List<Future<String>> futures = new ArrayList<>();

        // Submit tasks to fetch JSON data from each URL in parallel
        for (String url : urls) {
            Callable<String> task = new ParsingTask(url);
            Future<String> future = executorService.submit(task);
            futures.add(future);
        }

        // Wait for all tasks to complete and collect the parsed JSON data
        List<String> jsonDataList = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                String jsonData = future.get();
                jsonDataList.add(jsonData);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the executor service
        executorService.shutdown();

        // Process the parsed JSON data
        for (String jsonData : jsonDataList) {
            // Perform further processing or deserialization of JSON data here
            System.out.println(jsonData);
            System.out.println("");
        }
    }
}

// Define a parsing task to be executed in parallel
class ParsingTask implements Callable<String> {
    private final String url;

    public ParsingTask(String url) {
        this.url = url;
    }

//    @Override
//    public String call() throws Exception {
//        // Fetch JSON data from the URL
//        URL apiUrl = new URL(url);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(apiUrl.openStream()));
//        StringBuilder jsonData = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            jsonData.append(line);
//        }
//        reader.close();
//
//        // Perform JSON parsing or further processing here
//        return jsonData.toString();
//    }

    @Override
    public String call() throws IOException, InterruptedException {
        // Here, you can use Jsoup or any other library to parse the webpage
        // For demonstration purposes, let's just return the URL itself



        var client = HttpClient.newHttpClient();

        var builder =   HttpRequest.newBuilder(URI.create(url)).header("accept", "application/json");

//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                builder.setHeader(entry.getKey(), entry.getValue());
//            }

        var request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body(); //.map(String::new);




//            // Fetch the HTML of the webpage
//            Document doc = Jsoup.connect(url).get();
//
//            // Extract and print the title of the webpage
//            String title = doc.title();
//            System.out.println("Title: " + title);

        // Extract and print all the links on the webpage
//            Elements links = doc.select("a[href]");
//            System.out.println("Links:");
//            for (Element link : links) {
//                String linkHref = link.attr("href");
//                String linkText = link.text();
//                System.out.println(linkText + " - " + linkHref);
//            }
//
//            // Extract and print all paragraphs on the webpage
//            Elements paragraphs = doc.select("p");
//            System.out.println("Paragraphs:");
//            for (Element paragraph : paragraphs) {
//                System.out.println(paragraph.text());
//            }

        // Extract and print any other data you need


//
//        return url;
    }
}
