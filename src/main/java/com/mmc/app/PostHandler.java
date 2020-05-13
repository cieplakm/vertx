package com.mmc.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class PostHandler implements Handler<RoutingContext> {


    private final DataProvider dataProvider;

    public PostHandler(DataProvider dataProvider) {

        this.dataProvider = dataProvider;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.request()
                .bodyHandler(buffer -> {
                    String json = buffer.toString();

                    ObjectMapper objectMapper = new ObjectMapper();
                    WeatherData data;
                    try {
                        data = objectMapper.readValue(json, WeatherData.class);
                        log.info("New data temp: {}*C, pressure: {}hpa", data.getTemp(), data.getPressure());
                        String dataStr = System.currentTimeMillis() + ";" + data.getTemp() + ";" + data.getPressure() + "\n";
                        Files.write(Paths.get("db"), dataStr.getBytes(), StandardOpenOption.APPEND);
                        dataProvider.saveLast(dataStr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    routingContext.response()
                            .putHeader("Content-Type", "application/json")
                            .setStatusCode(200)
                            .end();
                });
    }
}
