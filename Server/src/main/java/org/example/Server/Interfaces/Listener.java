package org.example.Server.Interfaces;

import java.net.ServerSocket;

public interface Listener {
    void startAsync(ServerSocket server);
}
