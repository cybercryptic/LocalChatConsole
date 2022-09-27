package org.example.User.Interfaces;

public interface URTaskManager {

    void notifyNewUser(int id, String username);
    void broadcastMessage(int id, String username, String message);
}
