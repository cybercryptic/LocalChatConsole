package org.example.Server.Messengers.User;

import org.example.Server.UserManager.ActiveUsersManager;

public class UserBroadcaster {

    private final ActiveUsersManager manager;

    public UserBroadcaster(ActiveUsersManager manager) {
        this.manager = manager;
    }

    public void broadcast(int FromId, String message) {
        var users = manager.getUsers();
        if (users.isEmpty()) {
            System.out.println("No active users to send any message.");
            return;
        }

        for (var user : users) {
            if (user.getId() == FromId) continue;
            user.sendMessage(message);
        }
    }
}
