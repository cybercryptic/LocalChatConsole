package org.example.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerReader implements Runnable {
    private final Socket socket;

    public ServerReader(Socket socket) {
        this.socket = socket;
    }

    private void read() throws IOException {
        var dis = new DataInputStream(socket.getInputStream());

        var userName = dis.readUTF();

        var msg = "";
        while (!msg.equals("stop")) {
            msg = dis.readUTF();
            System.out.println(userName + ": "  + msg);
        }
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
