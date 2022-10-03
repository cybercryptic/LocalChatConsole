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
            case "sc" -> setServerCapacity(getScValue(input));
            case "help" -> showDetailedHelp();
            default -> showHelp();
        }
    }

    private int getScValue(String input) {
        var trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            System.out.println("Value cannot be empty");
            return -1;
        }

        var scValue = 0;
        try {
            scValue = Integer.parseInt(trimmedInput);
        } catch (Exception ex) {
            System.out.println("Value cannot be string");
            return -1;
        }

        return scValue;
    }

    private void setServerCapacity(int scValue) {
        if (scValue < 0) {
            System.out.println("Cannot set the value");
            return;
        }

        var scObj = server.getServerCapacity();
        var prevSc = scObj.get();
        if (prevSc == scValue) {
            System.out.println("Value cannot be same");
            return;
        }

        scObj.set(scValue);
        System.out.println("Changed server capacity " + prevSc + " -> " + scValue);
        if (scValue < prevSc)
            System.out.println("Connected users won't be affected by this change");
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
