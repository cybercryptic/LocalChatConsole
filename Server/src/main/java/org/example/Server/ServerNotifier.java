package org.example.Server;

public class ServerNotifier {

    private final ServerBroadcaster broadcaster;

    public ServerNotifier(ServerBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
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
