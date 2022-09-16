package org.example.Server;

import java.io.DataInputStream;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Need [Port]");
            return;
        }
        var server = new Server();
        server.start(Integer.parseInt(args[0]));
    }
    public void start(int port) {
        try (
                var server = new ServerSocket(port);
                var socket = server.accept();
                var inputStream = socket.getInputStream();
                var dataInputStream = new DataInputStream(inputStream)

                ) {

            System.out.println("Server started successfully");

            var clientMSG = "";

            while (!clientMSG.equals("stop")) {
                clientMSG = dataInputStream.readUTF();
                System.out.println("Client: " + clientMSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
