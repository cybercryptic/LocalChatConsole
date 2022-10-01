package org.example.UserManager;

import org.example.User.Main.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ActiveUsersManager {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger noOfUsers = new AtomicInteger();

    public void addUser(int id, User user) {
        users.putIfAbsent(id, user);
        noOfUsers.getAndIncrement();
    }

    public void removeUser(int id) {
        users.remove(id);
        noOfUsers.getAndDecrement();
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public boolean containsId(int id) {
        return users.containsKey(id);
    }

    public AtomicInteger usersSize() {
        return noOfUsers;
    }
}
