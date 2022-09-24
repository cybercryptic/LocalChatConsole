package org.example.Server;

import org.example.Server.Interfaces.Listener;
import org.example.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;

public class ServerListener extends ChatServer implements Listener {

    private final ServerUserManager userManager = new ServerUserManager();

    public void startAsync(ServerSocket server) {
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
        System.out.println(session.get());
        while (session.get() && areUsersUnderCapacity(SERVER_CAPACITY)) {
            var id = getId();
            System.out.println("Adding user: " + id);
            var socket = server.accept();
            System.out.println(socket.isClosed());
            System.out.println("Added user: " + id);
            var user = new User(id, socket);
            userManager.addUser(id, user);
            user.startReceiverAsync();
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
