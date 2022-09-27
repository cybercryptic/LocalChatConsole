package org.example.User;

import org.example.Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
    private final int id;
    private String username;
    private final Socket socket;
    private final DataOutputStream dos;
    private final Server server;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, Server server) throws IOException {
        this.id = id;
        this.socket = socket;
        this.server = server;

        dos = getDos();

        initialHelperClasses();
        startReceiver();
    }


    public void startReceiver() {
        receiver.startAsync();
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    private void initialHelperClasses() {
        receiver = new UserReceiver(this, server.userTaskManager);
        sender = new UserSender(this);
    }

    public void stop() throws IOException {
        dos.close();
        socket.close();
        server.userManager.removeUser(id);

        System.out.println(username + " disconnected");
    }

    public int getId() {
        return id;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected Socket getSocket() {
        return socket;
    }

    protected DataOutputStream getDos() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
