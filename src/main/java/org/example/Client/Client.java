package org.example.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private String userName;
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Need: [Username] [Server host] [Port]");
            return;
        }

        var client = new Client();
        client.setUsername(args[0]);
        client.start(args[1], Integer.parseInt(args[2]));
    }

    public void start(String host, int port) {
        try (var socket = new Socket(host, port)) {
            System.out.println("Connected to server successfully");

            sendUsernameToServer(socket);

            var readerThread = new Thread(new ClientReader(socket));
            var writerThread = new Thread(new ClientWriter(socket));

            readerThread.start();
            writerThread.start();

            while (readerThread.isAlive() && writerThread.isAlive())
                Thread.sleep(5000);

        } catch (IOException e) {
            waitUntil();
            start(host, port);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendUsernameToServer(Socket socket) throws IOException {
        new DataOutputStream(socket.getOutputStream()).writeUTF(userName);
    }

    private static void waitUntil() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}
