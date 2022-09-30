package org.example.Server.Main.Writer;

import org.example.Server.Main.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter {

    private final Server server;
    private final CommandCenter commandCenter;

    public ServerWriter(Server server, CommandCenter commandCenter) {
        this.server = server;
        this.commandCenter = commandCenter;
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
        commandCenter.execute(input);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
