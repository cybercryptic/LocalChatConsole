package org.example.Server;

import java.io.IOException;

public class ServerSender {

    private final UserManager userManager;

    public ServerSender(UserManager userManager) {
        this.userManager = userManager;
    }

    public void send(int id, String message) throws IOException {
        if (!userManager.containsId(id)) {
            System.out.println("Unknown user id: " + id);
            return;
        }

        userManager.getUser(id).sendMessage(message);
    }
}
