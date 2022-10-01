package org.example.Server.Main.Writer;

import org.example.Server.Main.Server;
import org.example.Server.Main.Writer.Commands.CMDCommand;
import org.example.Server.Main.Writer.Commands.HelpCommand;
import org.example.Server.Main.Writer.Commands.SETCommand;
import org.example.Server.Main.Writer.Commands.USRCommand;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;

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

    private void executeCommand(String[] filteredInput) throws IOException {
        if (filteredInput.length != 2) {
            System.out.println("Invalid command syntax");
            System.out.println("-h for help");
            return;
        }

        var command = filteredInput[1].toLowerCase();
        switch (command) {
            case "stop" -> stopServer();
            case "something" -> System.out.println("Do something here");
            default -> System.out.println("Invalid command \n use -h for help");
        }
    }

    private void stopServer() throws IOException {
        notifier.notifyServerShutdownToUsers();
        server.stop();
    }
}
