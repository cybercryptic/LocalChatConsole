package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    protected ServerSocket server;
    protected final AtomicInteger SERVER_CAPACITY = new AtomicInteger();
    protected final AtomicBoolean session = new AtomicBoolean();
    protected final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();


    private final ServerListener listener = new ServerListener();
    private final ServerWriter writer = new ServerWriter();

    public ChatServer() {

    }
    public ChatServer(int serverCapacity) {
        SERVER_CAPACITY.set(serverCapacity);
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        session.set(true);
        System.out.println("Server started successfully");

        listener.listenAsync();
        writer.writeAsync();

        waitUntilSessionEnds();

        System.out.println("Server closed successfully");
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

    public ConcurrentHashMap<Integer, User> getUsers() {
        return users;
    }

    public void stop() throws IOException {
        session.set(false);
        server.close();

        System.out.println("Server stopped successfully");
    }
}
