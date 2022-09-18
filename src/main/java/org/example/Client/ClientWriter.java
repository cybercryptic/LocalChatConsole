package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientWriter {

    private final Client client;
    private final Socket socket;
    private DataOutputStream dos;
    private BufferedReader buffReader;

    public ClientWriter(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    private void write() throws IOException {
        setDosNBuffReader();

        sendUsernameToServer();

        initiateWriteSession();

        close();
    }

    private void close() throws IOException {
        buffReader.close();
        dos.close();
    }

    private void initiateWriteSession() throws IOException {
        var message = "";
        while (client.getSession()) {
            message = buffReader.readLine();
            applyFilters(message);
            sendMessage(message);
        }
    }

    private void applyFilters(String message) {
        if (message.equals("stop")) client.setSession(false);
    }

    private void sendUsernameToServer() throws IOException {
        System.out.print("Enter your username: ");
        sendMessage(buffReader.readLine());
    }

    private void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    private void setDosNBuffReader() throws IOException {
        dos = getDos();
        buffReader = getBuffReader();
    }

    private BufferedReader getBuffReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private DataOutputStream getDos() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
