package org.example.Server;

public class ServerSender {

    private final UserManager userManager;
    private final ServerBroadcaster broadcaster;

    public ServerSender(UserManager userManager, ServerBroadcaster broadcaster) {
        this.userManager = userManager;
        this.broadcaster = broadcaster;
    }

    public void send(int id, String message) {
        if (!userManager.containsId(id)) {
            System.out.println("Unknown user id: " + id);
            return;
        }

        userManager.getUser(id).sendMessage("{" + "Server" + "}: " + message);
    }

    public void sendEveryoneExcept(int id, String username, String message) {
        var usrMessage = "[" + username + "]: " + message;
        broadcaster.broadcastExcept(usrMessage, id);
        System.out.println(id + "> " + username + ": " + message);
    }
}
