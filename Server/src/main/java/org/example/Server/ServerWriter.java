package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter {

    private final Server server;

    public ServerWriter(Server server) {
        this.server = server;
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
        var buffReader = getBufferedReader();

        var input = "";
        while (server.getSession().get()) {
            input = buffReader.readLine();
            execute(input);
        }

        buffReader.close();
    }

    private void execute(String input) throws IOException {
        server.actionCenter.execute(input);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
