package org.example.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private final AtomicBoolean session = new AtomicBoolean();
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Need: [Server host] [Port]");
            return;
        }

        var client = new Client();
        client.setSession(true);
        client.start(args[0], Integer.parseInt(args[1]));
    }

    public void start(String host, int port) throws IOException {
        System.out.println("Connecting to Server host: " + host + " Port: " + port);

        var socket = getSocket(host, port);

        System.out.println("Connected to server successfully");

        startReaderNWriter(socket);

        waitUntilSessionEnds();

        socket.close();
    }

    private void waitUntilSessionEnds() {
        while (getSession()) waitFor5s();
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void startReaderNWriter(Socket socket) {
        CompletableFuture.runAsync(() -> new ClientReader(this, socket).run());
        CompletableFuture.runAsync(() -> new ClientWriter(this, socket).run());
    }

    private Socket getSocket(String host, int port) {
        Socket socket;

        while (true) {
            try {
                socket = new Socket(host, port);
                break;
            } catch (IOException e) {
                waitFor5s();
            }
        }

        return socket;
    }

    public boolean getSession() {
        return session.get();
    }

    public void setSession(boolean session) {
        this.session.set(session);
    }
}
