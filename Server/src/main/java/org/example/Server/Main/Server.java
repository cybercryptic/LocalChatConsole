package org.example.Server.Main;

import org.example.Server.ActionCenter;
import org.example.Server.Messengers.MessageSenders.ServerBroadcaster;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.Messengers.MessageSenders.ServerSender;
import org.example.Server.UserManager.UserManager;
import org.example.Server.UserTaskManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server extends ServerHelpers {
    private ServerSocket server;
    private int serverCapacity;
    private final AtomicBoolean session = new AtomicBoolean();

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
        broadcaster = new ServerBroadcaster(userManager);
        notifier = new ServerNotifier(broadcaster);
        messenger = new ServerMessenger(this);
        listener = new ServerListener(this);
        writer = new ServerWriter(this);
        sender = new ServerSender(userManager);
        actionCenter = new ActionCenter(this);
        userTaskManager = new UserTaskManager(this);
    }

    protected ServerSocket getServer() {
        return server;
    }

    protected int getServerCapacity() {
        return serverCapacity;
    }

    public AtomicBoolean getSession() {
        return session;
    }
}
