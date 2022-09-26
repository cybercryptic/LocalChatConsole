package org.example.Server;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener {

    public void startAsync(Server server) {
        CompletableFuture.runAsync(() -> {
            try {
                listener(server);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void listener(Server server) throws IOException {
        var userManager = server.userManager;
        var serverSocket = server.getServer();

        while (areUsersUnderCapacity(userManager.usersSize())) {
            var id = getId(userManager);
            var socket = serverSocket.accept();
            server.userManager.addUser(id, new User(id, socket));
        }
    }

    private boolean areUsersUnderCapacity(int usersSize) {
        return usersSize < 3;
    }

    private int getId(UserManager userManager) {
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
