package org.example.Client;

import java.io.IOException;

public class ChatClient {
    private final Client client;

    public ChatClient(Client client) {
        this.client = client;
    }

    public void start(String host, int port) throws IOException {
        client.start(host, port);
        System.out.println("done");
        client.startListener();
        client.startWriter();

        waitUntilSessionEnds();

        client.stop();
    }



    private void waitUntilSessionEnds() {
        while (client.getSession().get()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
