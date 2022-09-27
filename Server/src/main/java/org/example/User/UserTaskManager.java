package org.example.User;

import org.example.Server.Server;
import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;

public class UserTaskManager implements UTaskManager, URTaskManager {

    private final Server server;

    public UserTaskManager(Server server) {
        this.server = server;
    }

    @Override
    public void notifyNewUser(int id, String username) {
        server.notifier.notifyNewUser(id, username);
    }

    @Override
    public void broadcastMessage(int id, String username, String message) {
        server.sender.sendEveryoneExcept(id, username, message);
    }

    @Override
    public void notifyUserExit(int id) {
        server.userManager.removeUser(id);
    }
}
