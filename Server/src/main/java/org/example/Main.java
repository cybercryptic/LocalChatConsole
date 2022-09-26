package org.example;

import org.example.Server.TheClean.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
        }

//        var server = new ChatServer();
//        server.start(3344, 100);
        var server = new Server(3344, 3);
        server.start();
    }
}