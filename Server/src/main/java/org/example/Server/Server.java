package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private ServerSocket server;
    private int serverCapacity;
    private final AtomicBoolean session = new AtomicBoolean();
    public UserManager userManager;
    private ServerListener listener;
    private ServerWriter writer;
    protected ServerSender sender;

    protected ActionCenter actionCenter;

    public Server() {
        initializeHelpers();
    }

    public void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        this.serverCapacity = serverCapacity;
        session.set(true);

        System.out.println("Server started successfully");
    }

    public void startListener() {
        listener.startAsync();
    }

    public void startWriter() {
        writer.startAsync();
    }

    public void stop() throws IOException {
        server.close();

        System.out.println("Server stopped successfully");
    }

    private void initializeHelpers() {
        userManager = new UserManager();
        listener = new ServerListener(this);
        writer = new ServerWriter(this);
        sender = new ServerSender(userManager);
        actionCenter = new ActionCenter(this);
    }

    protected ServerSocket getServer() {
        return server;
    }

    protected int getServerCapacity() {
        return serverCapacity;
    }

    protected AtomicBoolean getSession() {
        return session;
    }
}
