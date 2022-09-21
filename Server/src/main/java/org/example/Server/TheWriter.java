package org.example.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TheWriter {

    private final Server server;

    public TheWriter(Server server) {
        this.server = server;
    }

    private void write() {
        var buffReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

        }
    }
}
