package org.example;

import org.example.Client.ChatClient;
import org.example.Client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Need [host] [Port]");
            return;
        }

        var host = args[0];
        var port = Integer.parseInt(args[1]);

        var client = new Client(host, port);
        var chatClient = new ChatClient(client);
        chatClient.start();
    }
}