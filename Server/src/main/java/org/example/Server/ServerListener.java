package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener extends Configuration {

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
        while (areUsersUnderCapacity()) {
            var id = getId();
            var socket = server.accept();
            userManager.addUser(id, new User(id, socket));
        }
    }

    private boolean areUsersUnderCapacity() {
        return userManager.usersSize() < serverCapacity;
    }

    private int getId() {
        while (true) {
            var id = getRandomId();
            if (userManager.containsId(id)) continue;
            return id;
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * 100);
    }
}
