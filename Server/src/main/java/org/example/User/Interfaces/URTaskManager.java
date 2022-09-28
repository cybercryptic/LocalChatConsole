package org.example.User.Interfaces;

import org.example.User.User;

public interface URTaskManager {

    void notifyNewUser(User user);
    void broadcastMessage(int id, String username, String message);
}
