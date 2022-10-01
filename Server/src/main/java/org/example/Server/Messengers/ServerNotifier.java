package org.example.Server.Messengers;

import org.example.Server.Main.Server;
import org.example.Server.Messengers.Server.MessageSenders.ServerBroadcaster;
import org.springframework.stereotype.Component;

@Component
public class ServerNotifier {

    private final ServerBroadcaster broadcaster;

    public ServerNotifier(ServerBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void notifyServerShutdownToUsers() {
        broadcaster.broadcast("stop");
    }

    public void notifyNewUser(int id, String username) {
        var usrMessage = "[" + username + "] " + " joined the group";
        broadcaster.broadcastExcept(id, usrMessage);
        Server.console.print(id + "> " + username + " connected");
    }

    public void notifyDisconnectedUser(int id, String username) {
        var usrMessage = "[" + username + "] " + " disconnected";
        broadcaster.broadcastExcept(id, usrMessage);
        Server.console.print(username + " disconnected");
    }
}
