package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer extends Configuration {


    public void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        session.set(true);
        super.SERVER_CAPACITY = serverCapacity;
        System.out.println("Server started successfully");

        new ServerListener().startAsync(server);

        new ServerWriter().startAsync();



        waitUntilSessionEnds();

        stop();
    }

    private void waitUntilSessionEnds() {
        while (session.get()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void stop() throws IOException {
        session.set(false);
        server.close();
        System.out.println("Server stopped successfully");
    }
}
