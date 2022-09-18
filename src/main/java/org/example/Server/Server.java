package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private final AtomicBoolean session = new AtomicBoolean();
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
            return;
        }

        var server = new Server();
        server.setSession(true);
        server.start(Integer.parseInt(args[0]));
    }
    public void start(int port) throws IOException {
        var server = new ServerSocket(port);
        System.out.println("Server started successfully");

        var socket = server.accept();

        CompletableFuture.runAsync(() -> new ServerReader(this, socket).run());
        CompletableFuture.runAsync(() -> new ServerWriter(this, socket).run());

        while (getSession()) waitFor5s();

        socket.close();
        server.close();
        System.out.println("Server closed successfully");
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
}
