package org.example.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserReceiver {

    private final User user;

    public UserReceiver(User user) {
        this.user = user;
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
        var dis = user.getDis();
        var id = user.getId();

        var username = dis.readUTF();
        setUsernameNNotify(username);

        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                user.close();
                break;
            }
            System.out.println(id + "> " + username + ": " + message);
        }

        dis.close();
    }

    private void setUsernameNNotify(String username) {
        user.setUsername(username);
        System.out.println(username + " connected");
    }
}
