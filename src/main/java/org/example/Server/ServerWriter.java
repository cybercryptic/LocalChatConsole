package org.example.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWriter implements Runnable {

    private final Server server;
    private final Socket socket;

    public ServerWriter(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private void write() throws IOException {
        var dos = new DataOutputStream(socket.getOutputStream());
        var buffReader = new BufferedReader(new InputStreamReader(System.in));

        var message = "";
        while (server.getSession()) {
            message = buffReader.readLine();
            if (message.equals("stop")) server.setSession(false);
            sendMSG(dos, message);
        }

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
