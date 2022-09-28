package org.example.Server;

public class ServerBroadcaster {

    private final UserManager manager;

    public ServerBroadcaster(UserManager manager) {
        this.manager = manager;
    }

    public void broadcast(String message) {
        for (var user : manager.getUsers()) {
            user.sendMessage(message);
        }
    }

    public void broadcastExcept(String message, int IgnoreId) {
        for (var user : manager.getUsers()) {
            if (user.getId() == IgnoreId) continue;
            user.sendMessage(message);
        }
    }
}
