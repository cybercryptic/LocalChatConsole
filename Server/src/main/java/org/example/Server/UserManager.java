package org.example.Server;

import org.example.User.User;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public void addUser(int id, User user) {
        users.putIfAbsent(id, user);
    }

    public void removeUser(int id) {
        users.remove(id);
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

    public int usersSize() {
        return users.size();
    }
}
