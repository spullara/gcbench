# GCBench

GCBench is a simple HTTP server application designed to benchmark and monitor the performance of Java's Garbage Collection (GC) and overall application latency.

## Description

GCBench creates an HTTP server that responds to requests with a simple JSON payload. It uses virtual threads for request handling and measures the latency of each request. The application periodically prints latency statistics, making it useful for performance testing and monitoring.

## Features

- HTTP server responding with JSON payloads
- Uses Java's virtual threads for efficient concurrency
- Latency measurement for each request
- Periodic printing of latency statistics (50th, 99th, 99.9th percentiles, and max)
- Utilizes HdrHistogram for accurate latency recording

## Installation

To install and run GCBench, you need to have Java 24 (or later) and Maven installed on your system.

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/gcbench.git
   cd gcbench
   ```

2. Build the project using Maven:
   ```
   mvn clean package
   ```

## Usage

To run the application:

```
java -jar target/gcbench-1.0-SNAPSHOT.jar
```

The server will start on `localhost` port 8080. You can send HTTP requests to `http://localhost:8080/` to test the server.

## Dependencies

- Jackson Databind: For JSON serialization/deserialization
- HdrHistogram: For recording and analyzing latency data

## Contributing

Contributions to GCBench are welcome. Please feel free to submit a Pull Request.

## License

This project is open source. Please make sure you have the necessary rights to use and distribute this code before using it in your own projects.