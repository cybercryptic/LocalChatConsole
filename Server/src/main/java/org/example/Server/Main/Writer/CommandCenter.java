package org.example.Server.Main.Writer;

import org.example.Server.Main.Server;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;

import java.io.IOException;


public class CommandCenter {

    private final Server server;
    private final ServerNotifier notifier;
    private final ServerMessenger messenger;

    public CommandCenter(Server server, ServerNotifier notifier, ServerMessenger messenger) {
        this.server = server;
        this.notifier = notifier;
        this.messenger = messenger;
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

    private void executeCommand(String[] filteredInput) throws IOException {
        if (filteredInput.length != 2) {
            System.out.println("Invalid command syntax");
            System.out.println("-h for help");
            return;
        }

        var command = filteredInput[1].toLowerCase();
        switch (command) {
            case "stop" -> {
                notifier.notifyServerShutdownToUsers();
                server.stop();
            }
            case "something" -> System.out.println("Do something here");
            default -> System.out.println("Invalid command \n use -h for help");
        }
    }

    private void sendMessage(String[] filteredInput) {
        if (filteredInput.length != 3) {
            System.out.println("Invalid message sending syntax");
            System.out.println("-h for help");
            return;
        }

        var id = Integer.parseInt(filteredInput[1]);
        var message = filteredInput[2];
        messenger.sendFromServerTo(id, message);
    }
}
