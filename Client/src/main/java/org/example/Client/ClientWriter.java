package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ClientWriter {

    private final Client client;
    private DataOutputStream dos;

    public ClientWriter(Client client) {
        this.client = client;
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
        dos = getDos();
        var buffReader = getBuffReader();

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
