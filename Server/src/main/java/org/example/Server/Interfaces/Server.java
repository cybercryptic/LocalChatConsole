package org.example.Server.Interfaces;

import org.example.Server.Configuration;
import org.example.Server.ServerListener;
import org.example.Server.ServerWriter;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Server extends Configuration {
    private final Listener listener = new ServerListener();
    private final Writer writer = new ServerWriter();

    protected void start(int port, int serverCapacity) throws IOException {
        server = new ServerSocket(port);
        session.set(true);
        super.SERVER_CAPACITY = serverCapacity;
        System.out.println("Server started successfully");

        listener.startAsync();

        writer.startAsync();
    }

    protected void stop() throws IOException {
        session.set(false);
        server.close();
        System.out.println("Server stopped successfully");
    }

}
