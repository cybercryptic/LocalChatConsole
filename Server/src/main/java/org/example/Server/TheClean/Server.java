package org.example.Server.TheClean;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private final ServerSocket server;
    private final int SERVER_CAPACITY;
    private final AtomicBoolean session = new AtomicBoolean();
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();



    public Server(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        SERVER_CAPACITY = serverCapacity;
        session.set(true);
    }

    public void start() {
        System.out.println("Server started successfully");
        CompletableFuture.runAsync(() -> {
            try {
                listener();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        waitUntilSessionEnds();

        System.out.println(users);
    }

    private void waitUntilSessionEnds() {
        while (session.get()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void listener() throws IOException {
        while (areUsersUnderCapacity()) {
            var id = getId();
            System.out.println("Adding user: " + id);
            System.out.println("Count: " + Thread.activeCount());
            var socket = server.accept();
            users.putIfAbsent(id, new User(id, socket));
            System.out.println("Added user: " + id);
        }

        session.set(false);
    }

    private boolean areUsersUnderCapacity() {
        return users.size() <= SERVER_CAPACITY;
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
