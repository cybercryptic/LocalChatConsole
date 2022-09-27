package org.example.User;

import org.example.Server.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserReceiver {

    private final User user;
    private final Server server;
    private final int id;

    public UserReceiver(User user, Server server) {
        this.user = user;
        id = user.getId();
        this.server = server;
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

        var username = dis.readUTF();
        setUsernameNNotify(username);

        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                user.stop();
                break;
            }
            System.out.println(id + "> " + username + ": " + message);
        }

        dis.close();
    }

    private void setUsernameNNotify(String username) {
        user.setUsername(username);
        var usrMessage = username + " connected";
        server.broadcaster.broadcast(usrMessage);
        System.out.println(id + "> " + usrMessage);
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(user.getSocket().getInputStream());
    }
}
