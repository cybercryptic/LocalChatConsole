package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ClientListener {

    private final Client client;

    public ClientListener(Client client){
        this.client = client;
    }

    public void startAsync(ClientWriter writer) {
        CompletableFuture.runAsync(() -> {
            try {
                start(writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start(ClientWriter writer) throws IOException {
        var dis = getDis();


        var isRequestAccepted = Boolean.parseBoolean(dis.readUTF());

        System.out.println(isRequestAccepted);

        if (isRequestAccepted) {
            System.out.println("Writer started");
            writer.startAsync();
        }

        var message = "";
        while (isRequestAccepted) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                System.out.println("Server is disconnected");
                client.getSession().set(false);
                break;
            }
            System.out.println(message);
        }

        System.out.println("Server might be full, Try again!");

        dis.close();
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(client.getSocket().getInputStream());
    }
}
