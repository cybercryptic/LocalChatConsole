package org.example.Server;

import org.example.Server.Interfaces.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter extends Writer {

    private final ActionCenter actionCenter = new ActionCenter();

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
        System.out.println("Server writer started");

        var buffReader = getBufferedReader();

        var input = "";
        while (session.get()) {
            input = buffReader.readLine();
            execute(input);
        }

        buffReader.close();

        System.out.println("Server writer stopped");
    }

    private void execute(String input) throws IOException {
        actionCenter.execute(input);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
