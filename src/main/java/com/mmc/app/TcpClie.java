package com.mmc.app;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TcpClie {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        NetClient client = vertx.createNetClient();

        client.connect(8088, "localhost", netSocketAsyncResult -> {

            log.debug("Connection established.");

            NetSocket socket = netSocketAsyncResult.result();

            for (int j = 0; j < 10; j++) {
                String s = "Command" + j;
                Buffer b = Buffer.buffer(s.getBytes());
                socket.write(b);
            }

            log.debug("Data sent.");

            socket.handler(buffer -> {
                log.debug("Recived data from server with size: " + buffer.length());
            });

        });

    }

}
