package org.example.User;

import org.example.Server.ChatServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class User {

    private final int id;
    private final ChatServer chatServer;
    private final Socket socket;

    public User(int id, ChatServer chatServer, Socket socket) {
        this.id = id;
        this.chatServer = chatServer;
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
        chatServer.getUsers().remove(id);
    }
}
