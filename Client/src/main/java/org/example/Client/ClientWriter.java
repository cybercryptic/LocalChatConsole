package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ClientWriter {

    private final Client client;
    private final DataOutputStream dos;

    public ClientWriter(Client client) {
        this.client = client;
        dos = client.getDos();
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
        var buffReader = getBuffReader();

        sendUsernameToServer(buffReader);

        var message = "";
        while (client.getSession().get()) {
            message = buffReader.readLine();
            isStopReceived(message);
            sendMessage(message);
        }

        buffReader.close();
    }

    private void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
        dos.flush();
    }

    private void sendUsernameToServer(BufferedReader buffReader) throws IOException {
        System.out.print("Enter your username: ");
        sendMessage(buffReader.readLine());
    }


    private void isStopReceived(String message) throws IOException {
        if (message.equals("stop")) client.stop();

    }

    private BufferedReader getBuffReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
