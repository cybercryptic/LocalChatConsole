package org.example.User.Main;

import org.example.User.Interfaces.URHandler;
import org.example.User.Interfaces.UserHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

    private final int id;
    private String username;
    private final Socket socket;
    private DataOutputStream dos;
    private final UserHandler handler;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, UserHandler handler) throws IOException {
        this.id = id;
        this.socket = socket;
        this.handler = handler;

        setDos();

        initiateHelperClasses();
    }

    private void start() throws IOException {
        username = receiver.getUsername();
        handler.notifyNewUser(this);
        receiver.startAsync();
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public void isUserAllowed(String isAllowed) throws IOException {
        sendMessage(isAllowed);
        if (isAllowed.equals("true")) start();
    }

    public void stop() throws IOException {
        dos.close();
        socket.close();
        handler.notifyUserExit(this);
    }

    private void initiateHelperClasses() throws IOException {
        receiver = new UserReceiver(this, (URHandler) handler);
        sender = new UserSender(this);
    }

    protected Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    protected DataOutputStream getDos() {
        return dos;
    }

    private void setDos() throws IOException {
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User: " + username;
    }
}
