package org.example.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Need [Port]");
            return;
        }

        var server = new Server();
        server.start(Integer.parseInt(args[0]));
    }
    public void start(int port) throws IOException, InterruptedException {
        var server = new ServerSocket(port);
        System.out.println("Server started successfully");

        var socket = server.accept();

        var readerThread = new Thread(new ServerReader(socket));
        var writerThread = new Thread(new ServerWriter(socket));

        readerThread.start();
        writerThread.start();

        while (readerThread.isAlive() && writerThread.isAlive())
            Thread.sleep(5000);

        server.close();
        System.out.println("Server closed successfully");
    }
}
