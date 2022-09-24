package org.example.Server;

import org.example.Server.Interfaces.Listener;
import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener extends Listener {

    private final ServerUserManager userManager = new ServerUserManager();

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
        System.out.println("Server listener started");

        while (session.get() && areUsersUnderCapacity(SERVER_CAPACITY)) {
            var id = getId();
            var socket = server.accept();
            userManager.addUser(id, new User(id, socket));
        }

        System.out.println("Server listener stopped");
    }

    private boolean areUsersUnderCapacity(int capacity) {
        return Thread.activeCount() <= capacity;
    }

    private int getId() {
        while (true) {
            var id = getRandomId();
            if (users.containsKey(id)) continue;
            return id;
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * 100);
    }
}
