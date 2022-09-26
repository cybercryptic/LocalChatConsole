package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter extends Configuration {

    private ActionCenter actionCenter;

    public void startAsync(Server server) {
        actionCenter = new ActionCenter(server, new ServerSender(userManager));
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
