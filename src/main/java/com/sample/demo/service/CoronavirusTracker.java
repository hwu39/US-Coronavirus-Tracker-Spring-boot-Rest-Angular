package com.sample.demo.service;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class CoronavirusTracker {

    private String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";
    private HttpURLConnection connection;
    private List<LocationStats> allStats = new ArrayList<>();

    // Getter for Rest CoronavirusController
    public List<LocationStats> getAllStats() {
      System.out.println("Data should be available");
      return allStats;
    }

    @PostConstruct
    @Scheduled(cron="* 1 * * * *")
    public void fetchData() throws IOException, InterruptedException {
        
        List<LocationStats> newStats = new ArrayList<>();
        BufferedReader reader;
        BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv"));
        StringBuffer responseContent = new StringBuffer();
        int status;
        String line = null;
        URL url = new URL(DATA_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        status = connection.getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
        else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                writer.write(line + "\n");
                writer.flush();
            }
            reader.close();
            writer.close();
        }
        //System.out.println(responseContent.toString());

        Reader csvBodyReader = Files.newBufferedReader(Paths.get("data.csv"));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province_State"));
            locationStats.setCounty(record.get("Admin2"));
            locationStats.setCases(Integer.parseInt(record.get(record.size() - 1)));
            //System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats = newStats;
    }
}
