package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerWriter implements Runnable {

    private final Server server;

    public ServerWriter(Server server) {
        this.server = server;
    }

    private void write() throws IOException {
        initiateWriteSession();
    }

    private void initiateWriteSession() throws IOException {
        var buffReader = getBufferedReader();

        var message = "";
        while (server.getSession()) {
            message = buffReader.readLine();
            if (stopReceived(message)) continue;
            sendMessage(message);
        }

        buffReader.close();
    }

    private void sendMessage(String message) throws IOException {
        var msgArray = message.split(">");

        if (msgArray.length != 2) {
            System.out.println("Error: Wrong syntax");
            return;
        }

        var id = Integer.parseInt(msgArray[0]);
        var filteredMessage = msgArray[1].trim();
        server.getUsers().get(id).sendMessage(filteredMessage);
    }

    private boolean stopReceived(String message) {
        if (message.equals("stop")) server.setSession(false);
        return false;
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
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
