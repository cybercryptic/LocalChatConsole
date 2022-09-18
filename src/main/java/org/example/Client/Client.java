package org.example.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private String userName;
    private final AtomicBoolean session = new AtomicBoolean();
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Need: [Username] [Server host] [Port]");
            return;
        }

        var client = new Client();
        client.setSession(true);
        client.setUsername(args[0]);
        client.start(args[1], Integer.parseInt(args[2]));
    }

    public void start(String host, int port) {
        try (var socket = new Socket(host, port)) {
            System.out.println("Connected to server successfully");

            sendUsernameToServer(socket);

            CompletableFuture.runAsync(() -> new ClientReader(this, socket).run());
            CompletableFuture.runAsync(() -> new ClientWriter(this, socket).run());

            while (getSession()) waitFor5s();

        } catch (IOException e) {
            waitFor5s();
            start(host, port);
        }
    }

    private void sendUsernameToServer(Socket socket) throws IOException {
        new DataOutputStream(socket.getOutputStream()).writeUTF(userName);
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public boolean getSession() {
        return session.get();
    }

    public void setSession(boolean session) {
        this.session.set(session);
    }
}
