package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {

    private final AtomicBoolean session = new AtomicBoolean();
    private final ServerSocket server;
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public ChatServer(int port) throws IOException {
        server = new ServerSocket(port);

        setSession(true);
    }

    public Socket accept() throws IOException {
        return server.accept();
    }

    public void start() {
        System.out.println("Server started successfully");

        new ServerListener(this).startAsync();

        new ServerWriter(this).writeAsync();

        waitUntilSessionEnds();

        System.out.println("Server closed successfully");
    }

    private void waitUntilSessionEnds() {
        while (getSession()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSession(boolean session) {
        this.session.set(session);
    }

    public boolean getSession() {
        return session.get();
    }

    public ConcurrentHashMap<Integer, User> getUsers() {
        return users;
    }

    public int getServerCapacity() {
        return 100;
    }

    public void stop() throws IOException {
        setSession(false);
        server.close();
        System.exit(0);
    }
}
