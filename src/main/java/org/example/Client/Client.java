package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Need: [Server host] [Port]");
            return;
        }
        var client = new Client();
        client.start(args[0], Integer.parseInt(args[1]));
    }

    public void start(String host, int port) {
        try (
                var socket = new Socket(host, port);
                var outputStream = socket.getOutputStream();
                var dataOutputStream = new DataOutputStream(outputStream);
                var buffReader = new BufferedReader(new InputStreamReader(System.in))

                ) {

            System.out.println("Client started successfully");

            var clientMSG = "";

            while (!clientMSG.equals("stop")) {
                System.out.print("Write: ");
                clientMSG = buffReader.readLine();
                dataOutputStream.writeUTF(clientMSG);
                dataOutputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
