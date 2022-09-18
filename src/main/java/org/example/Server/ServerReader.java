package org.example.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerReader implements Runnable {

    private final Server server;
    private final Socket socket;
    private String username;

    public ServerReader(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private void read() throws IOException {
        var dis = getDis();

        username = getUsername(dis);

        initiateReadSession(dis);

        dis.close();
    }

    private void initiateReadSession(DataInputStream dis) throws IOException {
        var message = "";
        while (server.getSession()) {
            message = dis.readUTF();
            if (stopReceived(message)) break;
            print(message);
        }
    }

    private void print(String message) {
        System.out.println(username + ": " + message);
    }

    private boolean stopReceived(String message) {
        if (message.equals("stop")) {
            System.out.println(username + " disconnected");
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
