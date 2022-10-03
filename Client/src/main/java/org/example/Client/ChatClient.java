package org.example.Client;

import java.io.IOException;

public class ChatClient {
    private final Client client;

    public ChatClient(Client client) {
        this.client = client;
    }

    public void start() throws IOException {
        client.receiver.startReceivingAsync();
        client.writer.startAsync();

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
