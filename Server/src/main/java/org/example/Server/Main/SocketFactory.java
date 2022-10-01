package org.example.Server.Main;

import org.example.User.Interfaces.UserHandler;
import org.example.User.Main.User;
import org.example.UserManager.UserManager;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SocketFactory {

    private final Server server;
    private final UserManager userManager;
    private final UserHandler userHandler;

    public SocketFactory(Server server, UserManager userManager, UserHandler userHandler) {
        this.server = server;
        this.userManager = userManager;
        this.userHandler = userHandler;
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
        while (server.getSession().get()) {
            var id = getId();
            var socket = server.getServer().accept();
            var user = new User(id, socket, userHandler);
            if (!areUsersUnderCapacity()) {
                user.isUserAllowed("false");
                continue;
            }
            user.isUserAllowed("true");
            userManager.addConnectedUser(id, user);
        }
    }

    private boolean areUsersUnderCapacity() {
        var size = userManager.getNoOfUsers().get();
        return size < server.getServerCapacity();
    }

    private int getId() {
        while (true) {
            var id = getRandomId();
            if (userManager.containsId(id)) continue;
            return id;
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * server.getServerCapacity());
    }
}
