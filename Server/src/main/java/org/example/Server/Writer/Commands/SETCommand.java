package org.example.Server.Writer.Commands;

import org.example.Server.Writer.Commands.Interfaces.InputCommand;

public class SETCommand implements InputCommand {
    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Invalid SET Command syntax!");
            System.out.println("Type \"help\" for help!");
            return;
        }

        var filteredInput = input.trim().split(" ", 2);
        var option = filteredInput[0].trim().toLowerCase();
    }
}
