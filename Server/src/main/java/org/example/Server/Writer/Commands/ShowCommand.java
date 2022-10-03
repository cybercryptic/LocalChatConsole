package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;

public class ShowCommand extends InputCommand {

    private final Server server;

    public ShowCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            printHelp();
            return;
        }

        if (getFirstString(input).equals("sc")) printSC();
        else printHelp();

    }

    private void printSC() {
        System.out.println("Server capacity: " + server.getServerCapacity().get());
    }

    private void printHelp() {
        System.out.println("""
                
                Show command usage
                ------------------
                show [property] {Shows the property's value}
                
                Supported properties
                --------------------
                sc - Server capacity
                """);
    }
}
