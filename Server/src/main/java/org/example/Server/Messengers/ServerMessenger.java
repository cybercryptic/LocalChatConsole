package org.example.Server.Messengers;

import org.example.Server.Main.Server;
import org.example.Server.Messengers.MessageSenders.ServerBroadcaster;
import org.example.Server.Messengers.MessageSenders.ServerSender;
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
        sender.sendFromServerTo(toId, srvMessage);
    }

    public void sendFromServerToEveryone(String message) {
        var srvMessage = "{" + "Server" + "}: " + message;
        broadcaster.broadcast(srvMessage);
    }

    public void broadcastToGroupUsers(int fromId, String username, String message) {
        var usrMessage = "[" + username + "]: " + message;
        broadcaster.broadcastExcept(usrMessage, fromId);
        Server.console.print(fromId + "> " + username + ": " + message);
    }
}
