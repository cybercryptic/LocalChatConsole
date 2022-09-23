package org.example.Server;

import org.example.User.User;

import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Configuration {
    protected ServerSocket server;
    protected final AtomicInteger SERVER_CAPACITY = new AtomicInteger();
    protected final AtomicBoolean session = new AtomicBoolean();
    protected final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
}
