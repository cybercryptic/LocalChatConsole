package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private final AtomicBoolean session = new AtomicBoolean();

    public void start(int port) throws IOException {
        var server = getServer(port);

        System.out.println("Server started successfully");

        var socket = server.accept();

        startReaderNWriter(socket);

        waitUntilSessionEnds();

        close(server, socket);

        System.out.println("Server closed successfully");
    }

    private void close(ServerSocket server, Socket socket) throws IOException {
        socket.close();
        server.close();
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

    private void startReaderNWriter(Socket socket) {
        CompletableFuture.runAsync(() -> new ServerReader(this, socket).run());
        CompletableFuture.runAsync(() -> new ServerWriter(this, socket).run());
    }

    private ServerSocket getServer(int port) throws IOException {
        return new ServerSocket(port);
    }

    public void setSession(boolean session) {
        this.session.set(session);
    }

    public boolean getSession() {
        return session.get();
    }
}
