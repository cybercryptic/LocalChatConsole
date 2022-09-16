package org.example.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private final Server server;
    private final Socket socket;
    public Client(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (var dataInputStream = new DataInputStream(socket.getInputStream())) {

            var msg = "";

            while (!msg.equals("bye")) {
                msg = dataInputStream.readUTF();
                server.print("Client: " + msg);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
