package org.example.Server.Main.Writer.Commands;

import org.example.Server.Main.Writer.Commands.Interfaces.Command;

public class HelpCommand implements Command {

    public void execute() {
        System.out.println("""
                usr [id] [message] or
                usr [message]
                set [property] [value]
                cmd [command]
                help [Print help message & exit]
                """);
    }
}
