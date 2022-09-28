package org.example.Server.UserManager;

import org.example.User.User;

import java.util.concurrent.ConcurrentHashMap;

public class ActiveUserManager {
    private final ConcurrentHashMap<Integer, User> activeUsers = new ConcurrentHashMap<>();
}
