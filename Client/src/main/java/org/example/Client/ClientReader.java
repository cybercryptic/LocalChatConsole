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
        var dis = getDis();

        initiateReadSession(dis);

        dis.close();
    }

    private void initiateReadSession(DataInputStream dis) throws IOException {
        var message = "";
        while (client.getSession()) {
            message = dis.readUTF();
            applyFilters(message);
            print(message);
        }
    }

    private static void print(String message) {
        System.out.println("Server: " + message);
    }

    private void applyFilters(String message) throws IOException {
        if (message.equals("stop")) {
            System.out.println("Server is disconnected");
            client.setSession(false);
            socket.close();
            System.exit(0);
        }
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    public void run() {
        try {
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
