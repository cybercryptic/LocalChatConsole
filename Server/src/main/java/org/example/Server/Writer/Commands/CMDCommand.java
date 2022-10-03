package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;
import org.example.Server.Writer.Commands.Interfaces.InputCommand;

import java.io.IOException;

public class CMDCommand implements InputCommand {

    private final Server server;

    public CMDCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Invalid CMD Command syntax!");
            showHelp();
            return;
        }

        var filteredInput = input.trim().split(" ", 2);
        var command = filteredInput[0].trim().toLowerCase();

        if (command.equals("stop")) {
            try {
                stopServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showHelp();
        }
    }

    private void stopServer() throws IOException {
        server.stop();
    }

    private void showHelp() {
        System.out.println("Type \"help\" for help!");
    }
}
