package org.example.Server;

import java.io.IOException;

public class ServerSender extends Configuration {

    public void send(int id, String message) throws IOException {
        if (!users.containsKey(id)) {
            System.out.println("Unknown user id: " + id);
            return;
        }

        users.get(id).sendMessage(message);
    }
}
