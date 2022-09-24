package org.example.Server.TheClean;

import java.net.Socket;

public class User {
    private final int id;
    private final Socket socket;

    public User(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }
}
