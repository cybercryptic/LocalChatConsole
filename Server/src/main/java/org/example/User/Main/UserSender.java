package org.example.User.Main;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserSender {

    private final User user;

    public UserSender(User user) {
        this.user = user;
    }

    public void sendMessage(String message) {
        CompletableFuture.runAsync(() -> {
            try {
                send(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void send(String message) throws IOException {
        var dos = user.getDos();
        dos.writeUTF(message);
        dos.flush();
    }
}
