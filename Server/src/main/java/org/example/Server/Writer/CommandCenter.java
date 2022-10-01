package org.example.Server.Writer;

import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Communicators.ServerNotifier;
import org.example.Server.Main.Server;
import org.example.Server.Writer.Commands.CMDCommand;
import org.example.Server.Writer.Commands.HelpCommand;
import org.example.Server.Writer.Commands.SETCommand;
import org.example.Server.Writer.Commands.USRCommand;

import java.io.IOException;


public class CommandCenter {

    private final Server server;
    private final ServerNotifier notifier;
    private final ServerMessenger messenger;

    public CommandCenter(Server server, ServerNotifier notifier, ServerMessenger messenger) {
        this.server = server;
        this.notifier = notifier;
        this.messenger = messenger;
    }

    public void execute(String input) {
        if (input.trim().isEmpty()) return;

        var filteredInput = input.trim().split(" ", 2);
        var command = filteredInput[0].trim().toLowerCase();
        input = input.replace(command, "");

        switch (command) {
            case "usr" -> new USRCommand(messenger).execute(input);
            case "set" -> new SETCommand().execute(input);
            case "cmd" -> new CMDCommand().execute(input);
            case "help" -> new HelpCommand().execute();
            default -> System.out.println("-h for help");
        }
    }

    private void stopServer() throws IOException {
        notifier.notifyServerShutdownToUsers();
        server.stop();
    }
}
