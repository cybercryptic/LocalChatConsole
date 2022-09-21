package org.example;

import org.example.Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
            return;
        }

        var server = new Server(Integer.parseInt(args[0]));
        server.start();
    }
}