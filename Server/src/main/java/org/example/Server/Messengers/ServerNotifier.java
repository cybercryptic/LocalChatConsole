package org.example.Server.Messengers;

import org.example.Server.Main.Server;
import org.example.Server.Messengers.MessageSenders.ServerBroadcaster;

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
        broadcaster.broadcastExcept(usrMessage, id);
        Server.console.print(id + "> " + username + " connected");
    }

    public void notifyDisconnectedUser(int id, String username) {
        var usrMessage = "[" + username + "] " + " disconnected";
        broadcaster.broadcastExcept(usrMessage, id);
        Server.console.print(username + " disconnected");
    }
}
