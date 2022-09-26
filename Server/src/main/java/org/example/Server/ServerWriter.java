package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerWriter {

    private ActionCenter actionCenter;

    public void startAsync(Server server) {
        actionCenter = new ActionCenter(server, new ServerSender(server.userManager));
        CompletableFuture.runAsync(() -> {
            try {
                start(server.getSession());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start(AtomicBoolean session) throws IOException {
        var buffReader = getBufferedReader();

        var input = "";
        while (session.get()) {
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
