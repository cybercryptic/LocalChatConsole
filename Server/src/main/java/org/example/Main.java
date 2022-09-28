package org.example;

import org.example.Server.Main.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Need [Port] [Server capacity]");
            return;
        }

        var port = Integer.parseInt(args[0]);
        var serverCapacity = Integer.parseInt(args[1]);

        var server = new ChatServer(new Server());
        server.start(port, serverCapacity);
    }
}