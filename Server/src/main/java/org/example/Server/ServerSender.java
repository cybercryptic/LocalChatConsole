package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerSender {

    private final ConcurrentHashMap<Integer, User> users;

    public ServerSender(ChatServer chatServer) {
        users = chatServer.getUsers();
    }

    public void send(int id, String message) throws IOException {
        if (!users.containsKey(id)) {
            System.out.println("Unknown user id: " + id);
            return;
        }

        users.get(id).sendMessage(message);
    }
}
