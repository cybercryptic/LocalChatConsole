package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Configuration {
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
}
