package org.example;

import org.example.Server.Main.Server;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ChatServer {

    private final Server server;

    public ChatServer(Server server) {
        this.server = server;
    }

    public void start(int port, int serverCapacity) throws IOException {
        server.start(port, serverCapacity);

        server.startSocketFactory();
        server.startWriter();

        waitUntilSessionEnds();

        server.stop();
    }

    private void waitUntilSessionEnds() {
        while (server.getSession().get()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
