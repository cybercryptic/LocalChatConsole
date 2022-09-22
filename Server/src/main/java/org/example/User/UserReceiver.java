package org.example.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class UserReceiver implements Runnable {

    private final User user;
    private final Socket socket;
    private final int id;
    private String username;

    public UserReceiver(User user, Socket socket) {
        this.user = user;
        this.socket = socket;
        this.id = user.getId();
    }

    private void read() throws IOException {
        var dis = getDis();

        username = getUsername(dis);

        initiateReadSession(dis);

        dis.close();
    }

    private void initiateReadSession(DataInputStream dis) throws IOException {
        var message = "";
        while (true) {
            message = dis.readUTF();
            if (stopReceived(message)) break;
            print(message);
        }
    }

    private void print(String message) {
        System.out.println(id + "> " + username + ": " + message);
    }

    private boolean stopReceived(String message) throws IOException {
        if (message.equals("stop")) {
            System.out.println(username + " disconnected");
            user.close();
            return true;
        }

        return false;
    }

    private String getUsername(DataInputStream dis) throws IOException {
        return dis.readUTF();
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run () {
        try {
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
