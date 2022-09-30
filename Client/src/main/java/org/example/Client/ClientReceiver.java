package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ClientReceiver {

    private final DataInputStream dis;
    private final Client client;

    public ClientReceiver(Client client) throws IOException {
        this.client = client;
        dis = getDis();
    }

    public void startReceivingAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                startReceiving();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void startReceiving() throws IOException {
        var message = "";
        while (client.getSession().get()) {
            message = dis.readUTF();
            // TODO: (Violating single responsibility principal) Extract the class into 2.
            //  Here the receiver is doing 2 things, 1 Getting messages and printing
            //  2 Checking the message for stop
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
