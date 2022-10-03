package org.example.Server.Writer.Commands;

import org.example.Server.Writer.Commands.Interfaces.Command;

public class HelpCommand implements Command {

    public void execute() {
        System.out.println("""
                Explanation of commands
                -----------------------
                usr - To send or broadcast message
                set - To set properties of server
                cmd - To execute server commands
                help - To print this message & exit
                
                Syntax of commands
                ------------------
                usr [id] [message] or usr [message]
                set [property] [value]
                cmd [command]
                help [Print help message & exit]
                """);
    }
}
