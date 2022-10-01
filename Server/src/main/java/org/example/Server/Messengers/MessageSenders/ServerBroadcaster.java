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
        var users = manager.getUsers();
        if (users.isEmpty()) {
            System.out.println("No active users to send any message.");
            return;
        }

        for (var user : users) {
            user.sendMessage(message);
        }
    }

    public void broadcastExcept(int IgnoreId, String message) {
        var users = manager.getUsers();
        if (users.isEmpty()) {
            System.out.println("No active users to send any message.");
            return;
        }

        for (var user : users) {
            if (user.getId() == IgnoreId) continue;
            user.sendMessage(message);
        }
    }
}
