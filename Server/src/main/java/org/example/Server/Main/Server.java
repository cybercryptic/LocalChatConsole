package org.example.Server.Main;

import org.example.Server.ActionCenter;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.UserManager.UserManager;
import org.example.User.Interfaces.UTaskManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Server {
    private ServerSocket server;
    private int serverCapacity;
    private final AtomicBoolean session = new AtomicBoolean();

    private final ServerListener listener;
    private final ServerWriter writer;

    public final static Console console = new Console();

    public Server(UserManager userManager, UTaskManager uTaskManager,
                  ServerNotifier serverNotifier, ServerMessenger serverMessenger) {
        this.listener = new ServerListener(this, userManager, uTaskManager);
        var actionCenter = new ActionCenter(this, serverNotifier, serverMessenger);
        this.writer = new ServerWriter(this, actionCenter);
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
