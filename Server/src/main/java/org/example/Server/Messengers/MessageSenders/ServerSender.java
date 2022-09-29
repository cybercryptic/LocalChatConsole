package org.example.Server.Messengers.MessageSenders;

import org.example.Server.UserManager.ActiveUserManager;
import org.springframework.stereotype.Component;

@Component
public class ServerSender {

    private final ActiveUserManager userManager;

    public ServerSender(ActiveUserManager userManager) {
        this.userManager = userManager;
    }

    public void sendFromServerTo(int toId, String message) {
        if (!userManager.containsId(toId)) {
            System.out.println("Unknown user id: " + toId);
            return;
        }

        userManager.getUser(toId).sendMessage(message);
    }
}
