package org.example.Client;

import org.example.Client.receiver.ClientReceiver;
import org.example.Client.receiver.ReceiverCommandCenter;
import org.example.Client.writer.ClientWriter;
import org.example.Client.writer.WriterHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private Socket socket;
    private final AtomicBoolean session = new AtomicBoolean();
    public final ClientReceiver receiver;
    public final ClientWriter writer;
    public static final Console console = new Console();

    public Client(String host, int port) throws IOException {
        setServer(host, port);
        receiver = new ClientReceiver(this, new ReceiverCommandCenter(this));
        writer = new ClientWriter(this, new WriterHandler());
        start();
    }

    private void start() throws IOException {
        if (!receiver.isRequestAccepted())
            System.out.println("Server might be busy!! Cannot connect.\nTry Again later...");

        session.set(true);
        System.out.println("Connected to server successfully");
    }

    public void stop() throws IOException {
        socket.close();

        System.out.println("Closed successfully");
    }

    private void setServer(String host, int port) {
        System.out.println("Connecting to Server host: " + host + " Port: " + port);
        System.out.println("CTRL + C to stop");
        socket = connectToServerWith(host, port);
    }

    private Socket connectToServerWith(String host, int port) {
        Socket socket;

        while (true) {
            try {
                socket = new Socket(host, port);
                break;
            } catch (IOException e) {
                waitFor5s();
            }
        }

        return socket;
    }

    private void waitFor5s() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public AtomicBoolean getSession() {
        return session;
    }
}
