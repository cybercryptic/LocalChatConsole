package org.example.Server.Writer.Commands;

import org.example.Server.Main.Server;

public class SETCommand extends InputCommand {

    private final Server server;

    public SETCommand(Server server) {
        this.server = server;
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            printHelp();
            return;
        }

        var stringHolder = getFirstStringNRemove(input);
        var property = stringHolder.firstString();
        input = stringHolder.input();

        if (property.equals("sc")) setServerCapacity(getScValue(input));
        else printHelp();
    }

    private void setServerCapacity(int scValue) {
        if (scValue < 0) {
            System.out.println("Cannot set the value");
            return;
        }

        var atomicSC = server.getServerCapacity();
        var prevSc = atomicSC.get();

        atomicSC.set(scValue);
        System.out.println("Changed server capacity " + prevSc + " -> " + scValue);
        if (scValue <= prevSc)
            System.out.println("Connected users won't be affected by this change");
    }

    private int getScValue(String input) {
        var trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            System.out.println("Value cannot be empty");
            return -1;
        }

        var scValue = 0;
        try {
            scValue = Integer.parseInt(trimmedInput);
        } catch (Exception ex) {
            System.out.println("Value cannot be a string");
            return -1;
        }

        return scValue;
    }

    private void printHelp() {
        System.out.println("""
                
                SET command usage
                -----------------
                set [property] [value]
                
                Properties
                ----------
                sc - Server capacity [Takes number] {No of users can connect to server}
                
                Value - It means number or string depends upon property
                """);
    }
}
