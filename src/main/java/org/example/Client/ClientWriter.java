package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientWriter implements Runnable {

    private final Socket socket;

    public ClientWriter(Socket socket) {
        this.socket = socket;
    }

    private void write() throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        var buffReader = new BufferedReader(new InputStreamReader(System.in));

        var msg = "";
        while (!msg.equals("stop")) {
            msg = buffReader.readLine();
            sendMSG(dos, msg);
        }

        buffReader.close();
        dos.close();
    }

    private void sendMSG(DataOutputStream dos, String msg) throws IOException {
        dos.writeUTF(msg);
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
