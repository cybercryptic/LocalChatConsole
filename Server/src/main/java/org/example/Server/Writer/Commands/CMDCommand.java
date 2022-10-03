package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;

public class CMDCommand extends InputCommand {

    private final Server server;

    public CMDCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            printHelp();
            return;
        }

        if (getFirstString(input).equals("stop")) stopServer();
        else printHelp();
    }

    private void stopServer() {
        server.getSession().set(false);
    }

    private void printHelp() {
        System.out.println("""
                
                CMD command usage
                -----------------
                cmd [command] {Executes server commands}
                
                Supported commands
                ------------------
                stop - Stops the server
                """);
    }
}
