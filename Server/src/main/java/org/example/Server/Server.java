package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private ServerSocket server;
    private int serverCapacity;
    private final AtomicBoolean session = new AtomicBoolean();

    protected UserManager userManager = new UserManager();
    protected ServerListener listener = new ServerListener();
    protected ServerWriter writer = new ServerWriter();

    public void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        this.serverCapacity = serverCapacity;
        session.set(true);


    }

    public void stop() throws IOException {
        session.set(false);
        server.close();
    }

    public ServerSocket getServer() {
        return server;
    }

    public int getServerCapacity() {
        return serverCapacity;
    }

    public AtomicBoolean getSession() {
        return session;
    }
}
