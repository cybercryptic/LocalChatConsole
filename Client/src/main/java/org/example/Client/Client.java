package org.example.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private Socket socket;
    private final AtomicBoolean session = new AtomicBoolean();
    private DataOutputStream dos;
    private DataInputStream dis;
    private ClientListener listener;
    private ClientWriter writer;

    public Client() throws IOException {

        setDosNDis();

        initiateHelpers();
    }

    public void start(String host, int port) {
        System.out.println("Connecting to Server host: " + host + " Port: " + port);
        socket = connectToServerWith(host, port);
        session.set(true);
        System.out.println("Connected to server successfully");
    }

    public void startListener() {
        listener.startAsync();
    }

    public void startWriter() {
        writer.startAsync();
    }

    public void stop() throws IOException {
        dis.close();
        dos.close();
        session.set(false);
        socket.close();

        System.out.println("Closed successfully");
    }


    private void initiateHelpers() {
        listener = new ClientListener(this);
        writer = new ClientWriter(this);
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

    protected DataOutputStream getDos() {
        return dos;
    }

    protected DataInputStream getDis() {
        return dis;
    }

    private void setDosNDis() throws IOException {
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
    }

    public AtomicBoolean getSession() {
        return session;
    }
}
