package org.example.Server;

import org.example.User.User;

public class ServerUserManager extends Configuration {
    public void addUser(int id, User user) {
        users.putIfAbsent(id, user);
    }

    public void removeUser(int id) {
        users.remove(id);
    }
}
