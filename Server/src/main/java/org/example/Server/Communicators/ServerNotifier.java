package org.example.Server.Communicators;

import org.example.Server.Main.Server;
import org.example.User.Main.User;
import org.springframework.stereotype.Component;

@Component
public class ServerNotifier {

    private final ServerBroadcaster broadcaster;

    public ServerNotifier(ServerBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public void notifyServerShutdownToUsers() {
        var srvMessage = "{Server}: is offline";
        broadcaster.broadcast(srvMessage);
        Server.console.print("Server shutdown successfully");
    }

    public void notifyNewUser(User user) {
        var id = user.getId();
        var username = user.getUsername();
        var usrMessage = "[" + username + "] " + " joined the group";
        broadcaster.broadcastExcept(id, usrMessage);
        Server.console.print(id + "> " + username + " connected");
    }

    public void notifyDisconnectedUser(User user) {
        var id = user.getId();
        var username = user.getUsername();
        var usrMessage = "[" + username + "] " + " disconnected";
        broadcaster.broadcastExcept(id, usrMessage);
        Server.console.print(username + " disconnected");
    }
}
