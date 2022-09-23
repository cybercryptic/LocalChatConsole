package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter {

    private final ChatServer chatServer;
    private final ActionCenter actionCenter;

    public ServerWriter(ChatServer chatServer, ActionCenter actionCenter) {
        this.chatServer = chatServer;
        this.actionCenter = actionCenter;
    }

    public void writeAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                write();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void write() throws IOException {
        var buffReader = getBufferedReader();

        var input = "";
        while (chatServer.getSession()) {
            input = buffReader.readLine();
            execute(input);
        }

        buffReader.close();
    }

    private void execute(String input) throws IOException {
        actionCenter.execute(input);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
