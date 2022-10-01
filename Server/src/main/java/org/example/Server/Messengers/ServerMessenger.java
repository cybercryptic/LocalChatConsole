package org.example.Server.Messengers;

import org.example.Server.Main.Server;
import org.example.Server.Messengers.Server.MessageSenders.ServerBroadcaster;
import org.example.Server.Messengers.Server.MessageSenders.ServerSender;
import org.springframework.stereotype.Component;

@Component
public class ServerMessenger {

    private final ServerSender sender;
    private final ServerBroadcaster broadcaster;

    public ServerMessenger(ServerSender sender, ServerBroadcaster broadcaster) {
        this.sender = sender;
        this.broadcaster = broadcaster;
    }

    public void sendFromServerTo(int toId, String message) {
        var srvMessage = "{" + "Server" + "}: " + message;
        sender.send(toId, srvMessage);
    }

    public void sendFromServerToEveryone(String message) {
        var srvMessage = "{" + "Server" + "}: " + message;
        broadcaster.broadcast(srvMessage);
    }

    public void broadcastToGroupUsers(int fromId, String username, String message) {
        var usrMessage = "[" + username + "]: " + message;
        broadcaster.broadcastExcept(fromId, usrMessage);
        Server.console.print(fromId + "> " + username + ": " + message);
    }
}
