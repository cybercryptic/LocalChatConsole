package org.example.User;

import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

    private final int id;
    private String username;
    private final Socket socket;
    private DataOutputStream dos;
    private final UTaskManager taskManager;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, UTaskManager taskManager) throws IOException {
        this.id = id;
        this.socket = socket;
        this.taskManager = taskManager;

        setDos();

        initiateHelperClasses();
    }

    public void start() throws IOException {
        username = receiver.getUsername();
        taskManager.notifyNewUser(this);
        receiver.startAsync();
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public void stop() throws IOException {
        dos.close();
        socket.close();
        taskManager.notifyUserExit(this);
    }

    private void initiateHelperClasses() throws IOException {
        receiver = new UserReceiver(this, (URTaskManager)taskManager);
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
