package org.example.User;

import org.example.Server.ServerUserManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

    protected int id;
    protected String username;
    protected Socket socket;
    private final ServerUserManager userManager = new ServerUserManager();
    private final UserReceiver receiver = new UserReceiver();

    public User(int id, Socket socket) {
        this.id = id;
        this.socket = socket;

        startReceiverAsync();
    }
    public User() {}

    private void startReceiverAsync() {
        System.out.println("User receiver started");
        receiver.startAsync();
    }

    public void sendMessage(String message) throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(message);
        dos.flush();
        dos.close();

        System.out.println("Message sent to: " + username);
    }

    public void close() throws IOException {
        socket.close();
        userManager.removeUser(id);
    }
}
