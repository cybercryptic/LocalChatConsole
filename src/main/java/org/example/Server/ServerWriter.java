package org.example.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWriter implements Runnable {

    private final Server server;
    private final Socket socket;
    private DataOutputStream dos;
    private BufferedReader buffReader;

    public ServerWriter(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private void write() throws IOException {
        setDosNBuffReader();

        initiateWriteSession();

        close();
    }

    private void close() throws IOException {
        dos.close();
        buffReader.close();
    }

    private void initiateWriteSession() throws IOException {
        var message = "";
        while (server.getSession()) {
            message = buffReader.readLine();
            applyFilters(message);
            sendMessage(dos, message);
        }
    }

    private void sendMessage(DataOutputStream dos, String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    private void applyFilters(String message) {
        if (message.equals("stop")) server.setSession(false);
    }

    private void setDosNBuffReader() throws IOException {
        dos = getDos();
        buffReader = getBufferedReader();
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private DataOutputStream getDos() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
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
