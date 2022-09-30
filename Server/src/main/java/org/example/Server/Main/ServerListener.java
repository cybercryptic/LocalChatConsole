package org.example.Server.Main;

import org.example.Server.UserManager.UserManager;
import org.example.User.Interfaces.UTaskManager;
import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ServerListener {

    private int noOfUsers;
    private final Server server;
    private final UserManager userManager;
    private final UTaskManager uTaskManager;

    public ServerListener(Server server, UserManager userManager, UTaskManager uTaskManager) {
        this.server = server;
        this.userManager = userManager;
        this.uTaskManager = uTaskManager;
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
            var user = new User(id, socket, uTaskManager);
            userManager.addUser(id, user);
            noOfUsers++;
        }
    }

    private boolean areUsersUnderCapacity() {
        return noOfUsers < server.getServerCapacity();
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
