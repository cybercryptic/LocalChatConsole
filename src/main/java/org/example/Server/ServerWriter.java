package org.example.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWriter implements Runnable {
    private final Socket socket;

    public ServerWriter(Socket socket) {
        this.socket = socket;
    }

    private void write() throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        var buffReader = new BufferedReader(new InputStreamReader(System.in));

        sendMSG(dos, "Hello client!");

        var message = "";
        while (!message.equals("stop")) {
            message = buffReader.readLine();
            sendMSG(dos, message);
        }

        buffReader.close();
        dos.close();
    }

    private void sendMSG(DataOutputStream dos, String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    @Override
    public void run() {
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
