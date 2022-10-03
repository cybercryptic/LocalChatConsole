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
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Server {
    private ServerSocket server;
    private final AtomicInteger serverCapacity = new AtomicInteger();
    private final AtomicBoolean session = new AtomicBoolean();

    private final SocketFactory socketFactory;
    private final ServerWriter writer;
    private final ServerNotifier notifier;

    public final static Console console = new Console();

    public Server(UserManager userManager, UserHandler userHandler, ServerNotifier notifier,
                  ServerMessenger messenger) {
        this.socketFactory = new SocketFactory(this, userManager, userHandler);
        var commandCenter = new CommandCenter(this, messenger);
        this.writer = new ServerWriter(this, commandCenter);
        this.notifier = notifier;
    }

    public void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        this.serverCapacity.set(serverCapacity);
        session.set(true);

        System.out.println("Server started successfully");
    }

    public void startSocketFactory() {
        socketFactory.startAsync();
    }

    public void startWriter() {
        writer.startAsync();
    }

    public void stop() throws IOException {
        server.close();
        notifier.notifyServerShutdownToUsers();
    }

    protected ServerSocket getServer() {
        return server;
    }

    public AtomicInteger getServerCapacity() {
        return serverCapacity;
    }

    public AtomicBoolean getSession() {
        return session;
    }
}
