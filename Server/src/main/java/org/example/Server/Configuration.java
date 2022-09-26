package org.example.Server;

import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Configuration {
    protected ServerSocket server;
    protected int serverCapacity;
    protected final AtomicBoolean session = new AtomicBoolean();
    protected UserManager userManager = new UserManager();
}
