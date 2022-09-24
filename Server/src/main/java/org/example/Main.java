package org.example;

import org.example.Server.ChatServer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
        }

        var server = new ChatServer();
        server.start(3344, 100);
    }
}