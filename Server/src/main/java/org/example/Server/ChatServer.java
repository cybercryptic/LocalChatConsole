package org.example.Server;

import java.io.IOException;

public class ChatServer {

    private final Server server;

    public ChatServer(Server server) {
        this.server = server;
    }

    public void start(int port, int serverCapacity) throws IOException {
        server.start(port, serverCapacity);

        server.listener.startAsync(server);
        server.writer.startAsync(server);

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
