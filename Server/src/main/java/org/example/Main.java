package org.example;

import org.example.Server.Main.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Need [Port] [Server capacity]");
            return;
        }

        var port = Integer.parseInt(args[0]);
        var serverCapacity = Integer.parseInt(args[1]);
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        var server = context.getBean(Server.class);
        var chatServer = new ChatServer(server);
        chatServer.start(port, serverCapacity);
    }
}