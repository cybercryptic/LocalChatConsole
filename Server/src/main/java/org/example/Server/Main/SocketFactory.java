package org.example.Server.Main;

import org.example.Server.UserManager.UserManager;
import org.example.User.Interfaces.UTaskManager;
import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SocketFactory {

    private int noOfUsers;
    private final Server server;
    private final UserManager userManager;
    private final UTaskManager uTaskManager;

    public SocketFactory(Server server, UserManager userManager, UTaskManager uTaskManager) {
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
        while (server.getSession().get()) {
            var id = getId();
            var socket = server.getServer().accept();
            var user = new User(id, socket, uTaskManager);
            if (!areUsersUnderCapacity()) {
                user.sendMessage("false");
                continue;
            }
            user.sendMessage("true");
            user.start();
            userManager.addUser(id, user);
            // TODO: noOfUsers is not being updated
            //  after users disconnected from group.
            noOfUsers++;
        }
    }

    private boolean areUsersUnderCapacity() {
        return noOfUsers < server.getServerCapacity();
    }

    private int getId() {
        while (true) {
            var id = getRandomId();
            // TODO: Here only userManger is being checked but,
            //  in Active userManager also there will be users.
            if (userManager.containsId(id)) continue;
            return id;
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * server.getServerCapacity());
    }

    public int getNoOfUsers() {
        return noOfUsers;
    }

    public void setNoOfUsers(int noOfUsers) {
        this.noOfUsers = noOfUsers;
    }
}
