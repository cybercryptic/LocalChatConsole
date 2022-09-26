package org.example;

import org.example.Server.ChatServer;
import org.example.Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
        }

        var server = new ChatServer(new Server());
        server.start(3344, 3);
    }
}