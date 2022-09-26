package org.example.User;

import org.example.Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
    private final int id;
    private String username;
    private final Socket socket;
    private final Server server;
    private DataOutputStream dos;
    private DataInputStream dis;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, Server server) throws IOException {
        this.id = id;
        this.socket = socket;
        this.server = server;

        setDosNDis();

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
        receiver = new UserReceiver(this);
        sender = new UserSender(this);
    }

    public void close() throws IOException {
        dis.close();
        dos.close();
        socket.close();
        server.userManager.removeUser(id);

        System.out.println(username + " disconnected");
        server.userManager.showActiveUsers();
    }

    private void setDosNDis() throws IOException {
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
    }

    protected int getId() {
        return id;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected DataInputStream getDis() {
        return dis;
    }

    protected DataOutputStream getDos() {
        return dos;
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
