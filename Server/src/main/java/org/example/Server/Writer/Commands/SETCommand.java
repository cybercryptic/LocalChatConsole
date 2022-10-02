package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;
import org.example.Server.Writer.Commands.Interfaces.InputCommand;

public class SETCommand implements InputCommand {

    private final Server server;

    public SETCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Invalid SET Command syntax!");
            showHelp();
            return;
        }

        var filteredInput = input.trim().split(" ", 2);
        var property = filteredInput[0].trim().toLowerCase();
        input = input.replace(property, "");

        switch (property) {
            case "sc" -> setServerCapacity(Integer.parseInt(input.trim()));
            case "help" -> showDetailedHelp();
            default -> showHelp();
        }
    }

    private void setServerCapacity(int serverCapacity) {
        server.getServerCapacity().set(serverCapacity);
    }

    private void showDetailedHelp() {
        System.out.println("""
                ------------------------------
                Syntax: set [property] [value]
                Example: set sc 5
                ------------------------------
                """);
    }

    private void showHelp() {
        System.out.println("Type \"help\" for help!");
    }
}
