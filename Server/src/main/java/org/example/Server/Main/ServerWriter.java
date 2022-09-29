package org.example.Server.Main;

import org.example.Server.ActionCenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter {

    private final Server server;
    private final ActionCenter actionCenter;

    public ServerWriter(Server server, ActionCenter actionCenter) {
        this.server = server;
        this.actionCenter = actionCenter;
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

    private void execute(String input) {
        actionCenter.execute(input);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
