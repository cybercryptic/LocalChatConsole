package org.example.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserReceiver extends User {
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

        username = getUsername(dis);

        initiateReadSession(dis);

        dis.close();
    }

    private void initiateReadSession(DataInputStream dis) throws IOException {
        var message = "";
        while (true) {
            message = dis.readUTF();
            if (stopReceived(message)) break;
            print(message);
        }
    }

    private void print(String message) {
        System.out.println(id + "> " + username + ": " + message);
    }

    private boolean stopReceived(String message) throws IOException {
        if (message.equals("stop")) {
            System.out.println(username + " disconnected");
            close();
            return true;
        }

        return false;
    }

    private String getUsername(DataInputStream dis) throws IOException {
        return dis.readUTF();
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }
}
