package org.example.Server.Messengers.MessageSenders;

import org.example.Server.UserManager.ActiveUsersManager;
import org.springframework.stereotype.Component;

@Component
public class ServerBroadcaster {

    private final ActiveUsersManager manager;

    public ServerBroadcaster(ActiveUsersManager manager) {
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
