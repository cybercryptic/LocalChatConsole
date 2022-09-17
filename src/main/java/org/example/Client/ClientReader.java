package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReader implements Runnable {
    private final Socket socket;

    public ClientReader(Socket socket) {
        this.socket = socket;
    }

    private void read() throws IOException {
        var dis = new DataInputStream(socket.getInputStream());

        var msg = "";
        while (!msg.equals("stop")) {
            msg = dis.readUTF();
            System.out.println("Server: " + msg);
        }

        dis.close();
    }

    @Override
    public void run() {
        try {
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
