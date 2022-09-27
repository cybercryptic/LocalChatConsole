package org.example.User;

import org.example.Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
    private final int id;
    private String username;
    private final Socket socket;
    private final Server server;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, Server server) {
        this.id = id;
        this.socket = socket;
        this.server = server;

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
        receiver = new UserReceiver(this, server);
        sender = new UserSender(this);
    }

    public void stop() throws IOException {
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
