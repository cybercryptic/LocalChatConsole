package org.example.User;

import org.example.Server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
    private final int id;
    private final Socket socket;
    private final Server server;
    private final DataOutputStream dos;
    private UserReceiver receiver;
    private UserSender sender;

    public User(int id, Socket socket, Server server) throws IOException {
        this.id = id;
        this.socket = socket;
        this.server = server;
        dos = new DataOutputStream(socket.getOutputStream());

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
        dos.close();
        socket.close();
        server.userManager.removeUser(id);
    }

    protected int getId() {
        return id;
    }

    protected Socket getSocket() {
        return socket;
    }

    protected DataOutputStream getDos() {
        return dos;
    }
}
