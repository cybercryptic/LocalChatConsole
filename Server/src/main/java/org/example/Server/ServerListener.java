package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener extends ChatServer {

    public void listenAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start() throws IOException {
        while (session.get() && areUsersUnderCapacity(SERVER_CAPACITY.get())) {
            var id = getId();
            var socket = server.accept();
            users.putIfAbsent(id , new User(id, this, socket));
        }
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
