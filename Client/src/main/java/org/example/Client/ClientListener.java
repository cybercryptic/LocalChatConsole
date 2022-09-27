package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ClientListener {

    private final Client client;

    public ClientListener(Client client){
        this.client = client;
    }

    public void startAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start() throws IOException {
        var dis = getDis();

        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                System.out.println("Server is disconnected");
                client.getSession().set(false);
                break;
            }
            System.out.println(message);
        }

        dis.close();
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(client.getSocket().getInputStream());
    }
}
