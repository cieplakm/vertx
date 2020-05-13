package com.mmc.app;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpServ {

    private static MyHandler handler;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        NetServer server = vertx.createNetServer();

        server.connectHandler(socket -> {
            log.debug("New connection established with: socket address {}", socket.remoteAddress());

            handler = new MyHandler();
            socket.handler(handler);

            socket.closeHandler(v -> {
                log.debug("The socket has been closed.");
            });
        });

        server.listen(8088, "localhost", netServerAsyncResult -> {
            if (netServerAsyncResult.succeeded()) {
                log.debug("Server is now listening on actual port: " + server.actualPort());
            } else {
                log.debug("Failed to bind!");
            }
        });
    }

    @Slf4j
    static class MyHandler implements Handler<Buffer> {

        @Override
        public void handle(Buffer event) {
            byte[] bytes = event.getBytes();
            log.debug("Received data: {}", new String(bytes));
        }

    }

}
