package org.example.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReader {

    private final Client client;
    private final Socket socket;

    public ClientReader(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    private void read() throws IOException {
        var dis = new DataInputStream(socket.getInputStream());

        var message = "";
        while (client.getSession()) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                System.out.println("Server is disconnected");
                break;
            }
            System.out.println("Server: " + message);
        }

        dis.close();
    }

    public void run() {
        try {
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
