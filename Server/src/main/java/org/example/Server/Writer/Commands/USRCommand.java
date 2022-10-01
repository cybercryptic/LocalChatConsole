package org.example.Server.Writer.Commands;

import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Writer.Commands.Interfaces.InputCommand;

public class USRCommand implements InputCommand {

    private final ServerMessenger messenger;

    public USRCommand(ServerMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Need Id or Message");
            showHelp();
            return;
        }

        var filteredInput = input.trim().split(" ", 2);
        var option = filteredInput[0].trim().toLowerCase();
        var message = input.replace(option, "").trim();

        if (option.equals("help")) {
            showDetailedHelp();
            return;
        }

        try {
            var id = Integer.parseInt(option);
            sendToUser(id, message);
        } catch (Exception ex) {
            sendToEveryUser(input.trim());
        }
    }

    private void sendToUser(int id, String message) {
        if (message.trim().isEmpty()) {
            System.out.println("Cannot send empty message!");
            showHelp();
            return;
        }

        messenger.sendFromServerTo(id, message);
    }

    private void sendToEveryUser(String message) {
        if (message.trim().isEmpty()) {
            System.out.println("Cannot send empty message!");
            showHelp();
            return;
        }

        messenger.sendFromServerToEveryone(message);
    }

    private void showDetailedHelp() {
        System.out.println("""
                -----------------------------------------------------------------------------
                Syntax: usr [id] [message] {Sends message to particular user with the id}
                Example: usr 23 how are you
                Syntax: usr [message] {Sends message to every active user}
                Example: usr how are you
                -----------------------------------------------------------------------------
                """);
    }

    private void showHelp() {
        System.out.println("Type \"help\" for help!");
    }
}
