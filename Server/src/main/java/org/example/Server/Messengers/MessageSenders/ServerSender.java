package org.example.Server.Messengers.MessageSenders;

import org.example.Server.UserManager.ActiveUserManager;
import org.example.Server.UserManager.UserManager;

public class ServerSender {

    private final ActiveUserManager userManager;

    public ServerSender(UserManager userManager) {
        this.userManager = userManager.active;
    }

    public void sendFromServerTo(int toId, String message) {
        if (!userManager.containsId(toId)) {
            System.out.println("Unknown user id: " + toId);
            return;
        }

        userManager.getUser(toId).sendMessage(message);
    }
}
