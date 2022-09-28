package org.example.Server.Messengers;

import org.example.Server.Main.Server;

public class ServerMessenger {

    private final Server server;

    public ServerMessenger(Server server) {
        this.server = server;
    }

    public void sendFromServerTo(int toId, String message) {
        var srvMessage = "{" + "Server" + "}: " + message;
        server.sender.sendFromServerTo(toId, srvMessage);
    }

    public void broadcastToGroupUsers(int fromId, String username, String message) {
        var usrMessage = "[" + username + "]: " + message;
        server.broadcaster.broadcastExcept(usrMessage, fromId);
        Server.console.print(fromId + "> " + username + ": " + message);
    }
}
