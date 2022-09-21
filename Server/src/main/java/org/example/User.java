package org.example;

import org.example.Server.Server;
import org.example.Server.UserReceiver;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class User {

    private final Server server;
    private final Socket socket;

    public User(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        startReceiverAsync();
    }

    private void startReceiverAsync() {
        CompletableFuture.runAsync(() -> new UserReceiver(this, socket).run());
    }

}
