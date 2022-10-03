package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ClientReceiver {

    private final DataInputStream dis;
    private final Client client;
    private final ReceiverCommandCenter commandCenter;

    public ClientReceiver(Client client, ReceiverCommandCenter commandCenter) throws IOException {
        this.client = client;
        this.commandCenter = commandCenter;
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
        var rsvMsg = "";
        while (client.getSession().get()) {
            rsvMsg = dis.readUTF();
            commandCenter.execute(rsvMsg);
        }

        dis.close();
    }

    public boolean isRequestAccepted() throws IOException {
        return Boolean.parseBoolean(dis.readUTF());
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(client.getSocket().getInputStream());
    }
}
