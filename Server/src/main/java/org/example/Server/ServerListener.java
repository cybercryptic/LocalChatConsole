package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ServerListener {

    private final ChatServer chatServer;
    private final ConcurrentHashMap<Integer, User> users;

    public ServerListener(ChatServer chatServer) {
        this.chatServer = chatServer;
        users = chatServer.getUsers();
    }

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
        var CAPACITY = chatServer.getServerCapacity();
        var id = getId();
        while (areUsersUnderCapacity(CAPACITY)) {
            var socket = chatServer.accept();
            users.putIfAbsent(id , new User(id, chatServer, socket));
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
