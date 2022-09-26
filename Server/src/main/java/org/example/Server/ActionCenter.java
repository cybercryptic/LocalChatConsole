package org.example.Server;

import java.io.IOException;

public class ActionCenter {

    private final Server server;

    public ActionCenter(Server server) {
        this.server = server;
    }

    public void execute(String input) throws IOException {
        if (input.trim().isEmpty()) return;

        var filteredInput = input.trim().split(" ", 3);
        switch (filteredInput[0]) {
            case "-u" -> sendMessage(filteredInput);
            case "-c" -> executeCommand(filteredInput);
            case "-h" -> printHelp();
            default -> System.out.println("-h for help");
        }
    }

    private void printHelp() {
        System.out.println("""
                -u [id] [message]
                -c [command]
                -h Print help message & exit
                """);
    }

    private void executeCommand(String[] filteredInput) {
        if (filteredInput.length != 2) {
            System.out.println("Invalid command syntax");
            System.out.println("-h for help");
            return;
        }

        var command = filteredInput[1].toLowerCase();
        switch (command) {
            case "stop" -> {
                server.broadcaster.broadcast("stop");
                server.getSession().set(false);
            }
            case "something" -> System.out.println("Do something here");
            default -> System.out.println("Invalid command \n use -h for help");
        }
    }

    private void sendMessage(String[] filteredInput) throws IOException {
        if (filteredInput.length != 3) {
            System.out.println("Invalid message sending syntax");
            System.out.println("-h for help");
            return;
        }

        var id = Integer.parseInt(filteredInput[1]);
        var message = filteredInput[2];
        server.sender.send(id, message);
    }
}
