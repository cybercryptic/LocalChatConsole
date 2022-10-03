package org.example.Server.Writer;

import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Main.Server;
import org.example.Server.Writer.Commands.*;


public class CommandCenter extends InputCommand {

    private final Server server;
    private final ServerMessenger messenger;

    public CommandCenter(Server server, ServerMessenger messenger) {
        this.server = server;
        this.messenger = messenger;
    }

    public void execute(String input) {
        if (input.trim().isEmpty()) return;

        var stringHolder = getFirstStringNRemove(input);
        var command = stringHolder.firstString();
        input = stringHolder.input();

        switch (command) {
            case "usr" -> new USRCommand(messenger).execute(input);
            case "set" -> new SETCommand(server).execute(input);
            case "show" -> new ShowCommand(server).execute(input);
            case "cmd" -> new CMDCommand(server).execute(input);
            case "help" -> new HelpCommand().execute();
            default -> System.out.println("Type \"help\" for help!");
        }
    }
}
