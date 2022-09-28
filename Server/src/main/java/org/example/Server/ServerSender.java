package org.example.Server;

import org.example.Server.UserManager.UserManager;

public class ServerSender {

    private final UserManager userManager;
    private final ServerBroadcaster broadcaster;

    public ServerSender(UserManager userManager, ServerBroadcaster broadcaster) {
        this.userManager = userManager;
        this.broadcaster = broadcaster;
    }

    public void sendFromServerTo(int ToId, String message) {
        if (!userManager.containsId(ToId)) {
            System.out.println("Unknown user id: " + ToId);
            return;
        }

        userManager.getUser(ToId).sendMessage("{" + "Server" + "}: " + message);
    }

    public void sendEveryoneExcept(int id, String username, String message) {
        var usrMessage = "[" + username + "]: " + message;
        broadcaster.broadcastExcept(usrMessage, id);
        Server.console.print(id + "> " + username + ": " + message);
    }
}
