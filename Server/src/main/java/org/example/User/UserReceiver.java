package org.example.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class UserReceiver {

    private final Socket socket;
    private final int id;

    public UserReceiver(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
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
        var dis = new DataInputStream(socket.getInputStream());

        var username = dis.readUTF();

        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) break;
            System.out.println(id + "> " + username + ": " + message);
        }

        dis.close();
    }
}
