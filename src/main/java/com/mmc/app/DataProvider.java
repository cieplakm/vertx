package com.mmc.app;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProvider {

    private String last;

    private WeatherData getWeatherData(String l) {
        String[] split = l.split(";");
        return getWeatherData(split);
    }

    @SneakyThrows
    public List<WeatherData> get(LocalDateTime f, LocalDateTime t)  {

        List<WeatherData> wd = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("db"));

            wd = reader.lines()
                    .parallel()
                    .map(this::getWeatherData)
                    .sorted()
                    .filter(d -> {
                        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(d.getTime()), ZoneId.systemDefault());
                        return (date.isAfter(f) || date.isEqual(f)) && (date.isBefore(t) || date.isEqual(t));
                    })

                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wd;
    }

    private WeatherData getWeatherData(String[] split) {
        WeatherData weatherData = new WeatherData();
        weatherData.setTime(Long.parseLong(split[0]));
        weatherData.setPressure(Float.parseFloat(split[2]));
        weatherData.setTemp(Float.parseFloat(split[1]));
        return weatherData;
    }

    public void saveLast(String last) {

        this.last = last;
    }

    public WeatherData getLast(){
        if (last == null){
            return null;
        }
        return getWeatherData(last.split(";"));
    }

}
