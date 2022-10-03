package org.example.Server.Writer.Commands;

public class HelpCommand implements Command {

    @Override
    public void execute() {
        System.out.println("""
                
                Explanation of commands
                -----------------------
                usr - To send or broadcast message
                set - To set properties of server
                show - To show property value
                cmd - To execute server commands
                help - To print this message & exit
                """);
    }
}
