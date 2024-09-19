package com.sampullara.gcbench;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.HdrHistogram.Histogram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Main {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Histogram histogram = new Histogram(3);

    public static void main(String[] args) throws IOException {
        // Make an HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 100);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.createContext("/", exchange -> {
            long startTime = System.nanoTime();
            try {
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                Map<String, Object> object = new HashMap<>();
                object.put("hello", "world");
                byte[] response = OBJECT_MAPPER.writeValueAsBytes(object);
                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                exchange.getResponseBody().close();
                long duration = System.nanoTime() - startTime;
                histogram.recordValue(duration / 1000);  // Convert to microseconds
            }
        });
        server.start();

        // Start a thread to periodically print histogram stats
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);  // Print stats every 10 seconds
                    System.out.println("Latency histogram (microseconds):");
                    System.out.println("50th percentile: " + histogram.getValueAtPercentile(50));
                    System.out.println("99th percentile: " + histogram.getValueAtPercentile(99));
                    System.out.println("99.9th percentile: " + histogram.getValueAtPercentile(99.9));
                    System.out.println("Max: " + histogram.getMaxValue());
                    histogram.reset();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}