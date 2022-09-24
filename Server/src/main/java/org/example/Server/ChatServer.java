package org.example.Server;

import org.example.Server.Interfaces.Server;

import java.io.IOException;

public class ChatServer extends Server {

    public void start(int port, int serverCapacity) throws IOException {
        super.start(port, serverCapacity);

        waitUntilSessionEnds();

        super.stop();
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
}
