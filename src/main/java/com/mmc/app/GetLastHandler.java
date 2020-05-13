package com.mmc.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class GetLastHandler implements Handler<RoutingContext> {

    private final DataProvider dataProvider;

    public GetLastHandler(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("Content-Type", "application/json")
                    .end(objectMapper.writeValueAsString(dataProvider.getLast()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
