package org.example.Server;

import org.example.User.User;

import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Configuration {
    protected ServerSocket server;
    protected volatile int SERVER_CAPACITY;
    protected final AtomicBoolean session = new AtomicBoolean();
    protected final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
}
