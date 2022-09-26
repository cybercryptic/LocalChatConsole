package org.example.Server;

import java.io.*;
import java.net.Socket;

public class User {
    private final int id;
    private final Socket socket;
    private final DataOutputStream dos;

    public User(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        dos = new DataOutputStream(socket.getOutputStream());

        startReceiver();
    }

    public void startReceiver() {
        new UserReceiver(socket, id).startAsync();
    }

    public void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
        System.out.println("Message sent!!");
    }

    public void close() throws IOException {
        dos.close();
        socket.close();
    }
}
