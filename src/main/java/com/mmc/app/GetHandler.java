package com.mmc.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GetHandler implements Handler<RoutingContext> {

     private final DataProvider dataProvider;

    public GetHandler(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        MultiMap params = routingContext.request().params();
        Long from = Long.parseLong(params.get("from"));
        Long to = Long.parseLong(params.get("to"));

        LocalDateTime f = LocalDateTime.ofInstant(Instant.ofEpochMilli(from), ZoneId.systemDefault());
        LocalDateTime t = LocalDateTime.ofInstant(Instant.ofEpochMilli(to), ZoneId.systemDefault());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json")
                    .end(objectMapper.writeValueAsString(dataProvider.get(f, t)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
