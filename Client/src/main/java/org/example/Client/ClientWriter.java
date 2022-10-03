package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ClientWriter {

    private final DataOutputStream dos;
    private final BufferedReader buffReader;
    private final Client client;
    private final WriterHandler handler;

    public ClientWriter(Client client, WriterHandler handler) throws IOException {
        this.client = client;
        this.handler = handler;
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
        if (client.getSession().get())
            handler.sendUsernameToServer(buffReader, dos);

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
