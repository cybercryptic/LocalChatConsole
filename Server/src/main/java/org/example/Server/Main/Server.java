package org.example.Server.Main;

import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Communicators.ServerNotifier;
import org.example.Server.Writer.CommandCenter;
import org.example.Server.Writer.ServerWriter;
import org.example.User.Interfaces.UserHandler;
import org.example.UserManager.UserManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Server {
    private ServerSocket server;
    private int serverCapacity;
    private final AtomicBoolean session = new AtomicBoolean();

    private final SocketFactory socketFactory;
    private final ServerWriter writer;

    public final static Console console = new Console();

    public Server(UserManager userManager, UserHandler userHandler, ServerNotifier notifier,
                  ServerMessenger messenger) {
        this.socketFactory = new SocketFactory(this, userManager, userHandler);
        var commandCenter = new CommandCenter(this, notifier, messenger);
        this.writer = new ServerWriter(this, commandCenter);
    }

    public void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        this.serverCapacity = serverCapacity;
        session.set(true);

        System.out.println("Server started successfully");
    }

    public void startListener() {
        socketFactory.startAsync();
    }

    public void startWriter() {
        writer.startAsync();
    }

    public void stop() throws IOException {
        server.close();
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
