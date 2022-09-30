package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ClientWriter {

    // TODO: ClientWriter name needs to change to appropriate name
    // TODO: ClientWriter needs CommandCenter and TaskManger Like classes to Execute input

    private final DataOutputStream dos;
    private final BufferedReader buffReader;
    private final Client client;

    public ClientWriter(Client client) throws IOException {
        this.client = client;
        buffReader = getBuffReader();
        dos = getDos();
    }

    public void startAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start() throws IOException {
        // TODO: (Violating Single Responsibility principal)
        //  1. Sending username to server
        //  2. Checking outgoing messages
        //  3. Sending outgoing message

        if (client.getSession().get())
            sendUsernameToServer(buffReader);

        var message = "";
        while (client.getSession().get()) {
            message = buffReader.readLine();
            isStopReceived(message);
            sendMessage(message);
        }

        dos.close();
        buffReader.close();
    }

    private void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    private void sendUsernameToServer(BufferedReader buffReader) throws IOException {
        var username = validateUsername(buffReader);
        sendMessage(username);
    }

    private String validateUsername(BufferedReader buffReader) throws IOException {
        String username;
        do {
            username = getUsername(buffReader);
        } while (username.trim().isEmpty());
        return username;
    }

    private String getUsername(BufferedReader buffReader) throws IOException {
        System.out.print("Enter your username: ");
        return buffReader.readLine();
    }


    private void isStopReceived(String message) {
        if (message.equals("stop")) client.getSession().set(false);

    }

    private DataOutputStream getDos() throws IOException {
        return new DataOutputStream(client.getSocket().getOutputStream());
    }

    private BufferedReader getBuffReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
