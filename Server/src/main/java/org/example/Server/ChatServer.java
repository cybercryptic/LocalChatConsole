package org.example.Server;

import org.example.User.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {

    private final AtomicBoolean session = new AtomicBoolean();
    private ServerSocket server;
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final ServerListener listener;
    private final ServerWriter writer;

    public ChatServer(ServerListener listener, ServerWriter writer) {
        this.listener = listener;
        this.writer = writer;
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);

        setSession(true);

        System.out.println("Server started successfully");

        listener.startAsync();

        writer.writeAsync();

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

    public Socket accept() throws IOException {
        return server.accept();
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
