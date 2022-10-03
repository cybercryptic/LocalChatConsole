package org.example.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class WriterHandler {

    public void sendUsernameToServer(BufferedReader buffReader, DataOutputStream dos) throws IOException {
        var username = validateUsername(buffReader);
        dos.writeUTF(username);
        dos.flush();
    }

    private String validateUsername(BufferedReader buffReader) throws IOException {
        String username;
        do {
            username = getUsername(buffReader);
        } while (username.trim().isEmpty());
        return username;
    }

    private String getUsername(BufferedReader buffReader) throws IOException {
        System.out.print("Enter your username: ");
        return buffReader.readLine();
    }
}
