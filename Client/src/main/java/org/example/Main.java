package org.example;

import org.example.Client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Need: [Server host] [Port]");
            return;
        }

        var client = new Client();
        client.setSession(true);
        client.start(args[0], Integer.parseInt(args[1]));
    }
}