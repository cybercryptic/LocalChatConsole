package org.example.Server.Writer.Commands;

import org.example.Server.Writer.Commands.Interfaces.Command;

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
