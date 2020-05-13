package com.mmc.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewServ extends AbstractVerticle {

    public static void main(String[] args) throws Exception {

        DeploymentOptions deploymentOptions = new DeploymentOptions();

        Vertx vertx = Vertx.vertx();
//        vertx     .deployVerticle("com.mmc.app.NewServ");
        vertx
                .deployVerticle("com.mmc.app.NewServ", deploymentOptions);

    }

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route(HttpMethod.GET, "/test")
                .handler(event -> {
                    log.debug("Request");
                    event.response().end();
                });

        server.requestHandler(router::accept)
                .listen(8081);
    }
}
