package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientWriter {

    private final Client client;
    private final Socket socket;

    public ClientWriter(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    private void write() throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        var buffReader = new BufferedReader(new InputStreamReader(System.in));

        var message = "";
        while (client.getSession()) {
            message = buffReader.readLine();
            if (client.getSession() && !message.isEmpty())
                System.out.println("Cannot send message!");
            if (message.equals("stop")) client.setSession(false);
            sendMSG(dos, message);
        }

        buffReader.close();
        dos.close();
    }

    private void sendMSG(DataOutputStream dos, String msg) throws IOException {
        dos.writeUTF(msg);
        dos.flush();
    }

    public void run() {
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
