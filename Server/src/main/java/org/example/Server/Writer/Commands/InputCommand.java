package org.example.Server.Writer.Commands;

public abstract class InputCommand {
    protected record StringHolder(String firstString, String input) {
    }
    
    protected abstract void execute(String input);
    
    public String getFirstString(String input) {
        var inputArray = input.trim().split(" ", 2);
        return inputArray[0].trim().toLowerCase();
    }
    
    public StringHolder getFirstStringNRemove(String input) {
        var string = getFirstString(input);
        input = input.trim().replace(string, "");
        return new StringHolder(string, input);
    }
}
