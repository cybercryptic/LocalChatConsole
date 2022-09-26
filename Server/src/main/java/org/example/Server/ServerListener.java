package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener {

    private final Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    public void startAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                listener();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void listener() throws IOException {
        while (server.getSession().get() && areUsersUnderCapacity()) {
            var id = getId();
            var socket = server.getServer().accept();
            server.userManager.addUser(id, new User(id, socket, server));
        }
    }

    private boolean areUsersUnderCapacity() {
        return server.userManager.usersSize() < server.getServerCapacity();
    }

    private int getId() {
        while (true) {
            var id = getRandomId();
            if (server.userManager.containsId(id)) continue;
            return id;
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * 100);
    }
}
