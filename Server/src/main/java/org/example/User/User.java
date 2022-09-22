package org.example.User;

import org.example.Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class User {

    private final int id;
    private final Server server;
    private final Socket socket;

    public User(int id, Server server, Socket socket) {
        this.id = id;
        this.server = server;
        this.socket = socket;

        startReceiverAsync();
    }

    private void startReceiverAsync() {
        CompletableFuture.runAsync(() -> new UserReceiver(this, socket).run());
    }

    public void sendMessage(String message) throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(message);
        dos.flush();
        dos.close();
    }

    public int getId() {
        return id;
    }

    public void close() throws IOException {
        socket.close();
        server.getUsers().remove(id);
    }
}
