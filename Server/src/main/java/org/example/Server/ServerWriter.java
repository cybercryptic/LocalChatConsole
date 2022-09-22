package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

public class ServerWriter {

    private final ChatServer chatServer;

    public ServerWriter(ChatServer chatServer) {
        this.chatServer = chatServer;
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
        var filteredInput = input.split(" ", 3);
        if (filteredInput.length != 2) {
            System.out.println("Invalid input!!! \n -h for help");
            return;
        }

        switch (filteredInput[0]) {
            case "-u" -> sendMessage(filteredInput);
            case "-c" -> executeCommand(filteredInput);
            case "-h" -> System.out.println("Print help");
            default -> System.out.println("Invalid syntax! \n use -h for help");
        }
    }

    public void executeCommand(String[] filteredInput) throws IOException {
        if (filteredInput.length != 2) {
            System.out.println("Invalid command syntax");
            System.out.println("-h for help");
            return;
        }

        var command = filteredInput[1].toLowerCase();
        switch (command) {
            case "stop" -> chatServer.stop();
            case "something" -> System.out.println("Do something here");
            default -> System.out.println("Invalid syntax! \n use -h for help");
        }
    }

    private void sendMessage(String[] filteredInput) throws IOException {
        if (filteredInput.length != 3) {
            System.out.println("Invalid message sending syntax");
            System.out.println("-h for help");
        }

        var id = Integer.parseInt(filteredInput[1]);
        var message = filteredInput[2];
        new ServerSender(chatServer).send(id, message);
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
