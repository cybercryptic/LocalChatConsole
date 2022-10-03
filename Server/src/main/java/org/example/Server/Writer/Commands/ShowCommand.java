package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;
import org.example.Server.Writer.Commands.Interfaces.InputCommand;

public class ShowCommand implements InputCommand {

    private final Server server;

    public ShowCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Invalid Show Command syntax!");
            showHelp();
            return;
        }

        var filteredInput = input.trim().split(" ", 2);
        var command = filteredInput[0].trim().toLowerCase();

        if ("sc".equals(command)) {
            printSC();
        } else {
            showHelp();
        }
    }

    private void printSC() {
        System.out.println("Server capacity: " + server.getServerCapacity().get());
    }

    private void showHelp() {
        System.out.println("Type \"help\" for help!");
    }
}
