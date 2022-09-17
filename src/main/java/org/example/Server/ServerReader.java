package org.example.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerReader implements Runnable {

    private final Server server;
    private final Socket socket;

    public ServerReader(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private void read() throws IOException {
        var dis = new DataInputStream(socket.getInputStream());

        var userName = dis.readUTF();

        var message = "";
        while (server.getSession()) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                System.out.println(userName + " disconnected");
                continue;
            }
            System.out.println(userName + ": " + message);
        }

        dis.close();
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
