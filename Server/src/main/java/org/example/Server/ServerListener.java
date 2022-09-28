package org.example.Server;

import org.example.User.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class ServerListener {

    private final Server server;
    private final UserManager userManager;

    public ServerListener(Server server) {
        this.server = server;
        userManager = server.userManager;
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
            if (!areUsersUnderCapacity()) {
                sendCapacityReachedAlert(socket);
                break;
            }
            userManager.addUser(id, new User(id, socket, server.userTaskManager));
        }
    }

    private void sendCapacityReachedAlert(Socket socket) throws IOException {
        var capacityReachedAlert = "Server capacity reached!!!\n" +
                "Ask admin or wait for other user to exit!";
        new DataOutputStream(socket.getOutputStream()).writeUTF(capacityReachedAlert);
        new DataOutputStream(socket.getOutputStream()).writeUTF("stop");
        socket.close();
    }

    private boolean areUsersUnderCapacity() {
        return userManager.usersSize() < server.getServerCapacity();
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
