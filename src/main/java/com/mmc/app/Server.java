package com.mmc.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server extends AbstractVerticle {

    private final DataProvider dataProvider = new DataProvider();

    public static void main(String[] args) throws Exception {
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        Vertx.vertx()
                .deployVerticle(new Server(), deploymentOptions);
    }

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route(HttpMethod.POST, "/temp")
                .handler(new PostHandler(dataProvider));

        router.route(HttpMethod.GET, "/temp")
                .handler(new GetHandler(dataProvider));

        router.route(HttpMethod.GET, "/temp/last")
                .handler(new GetLastHandler(dataProvider));

        server.requestHandler(router::accept)
                .listen(8080);
    }
    
}
