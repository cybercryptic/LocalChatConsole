package org.example.Server.Communicators;

import org.example.UserManager.ActiveUsersManager;
import org.springframework.stereotype.Component;

@Component
public class ServerSender {

    private final ActiveUsersManager userManager;

    public ServerSender(ActiveUsersManager userManager) {
        this.userManager = userManager;
    }

    public void send(int toId, String message) {
        if (!userManager.containsId(toId)) {
            System.out.println("Unknown user id: " + toId);
            return;
        }

        userManager.getUser(toId).sendMessage(message);
    }
}
