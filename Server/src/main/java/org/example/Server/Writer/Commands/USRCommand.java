package org.example.Server.Writer.Commands;

import org.example.Server.Communicators.ServerMessenger;

public class USRCommand extends InputCommand {

    private final ServerMessenger messenger;

    public USRCommand(ServerMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            printHelp();
            return;
        }

        var stringHolder = getFirstStringNRemove(input);
        var id = stringHolder.firstString();
        var message = stringHolder.input();

        try {
            sendToUser(Integer.parseInt(id), message);
        } catch (Exception ex) {
            sendToEveryUser(input.trim());
        }
    }

    private void sendToUser(int id, String message) {
        if (message.trim().isEmpty()) {
            System.out.println("Cannot send empty message!");
            return;
        }

        messenger.sendFromServerTo(id, message);
    }

    private void sendToEveryUser(String message) {
        if (message.trim().isEmpty()) {
            System.out.println("Cannot send empty message!");
            return;
        }

        messenger.sendFromServerToEveryone(message);
    }

    private void printHelp() {
        System.out.println("""
                
                USR command usage
                -----------------
                usr [id] [message] {Sends a message to a specific user with id}
                usr [message] {Sends a message to every active user}
                """);
    }
}
