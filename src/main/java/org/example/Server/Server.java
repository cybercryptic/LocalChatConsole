package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket server;
    private final ConcurrentHashMap<Socket, Client> clients = new ConcurrentHashMap<>();
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
            return;
        }
        var server = new Server();
        server.start(Integer.parseInt(args[0]));
    }
    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started successfully");

        CompletableFuture.runAsync(this::startListener);
        for (var client : clients.keySet())
            clients.get(client).run();

        System.out.println("Server closed successfully");
    }

    public void startListener() {
        while (Thread.activeCount() <= 100) {
            try {
                var socket = server.accept();
                clients.putIfAbsent(socket, new Client(this, socket));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void print(String msg) {
        System.out.println(msg);
    }
}
