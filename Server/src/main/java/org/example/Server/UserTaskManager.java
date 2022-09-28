package org.example.Server;

import org.example.Server.Main.Server;
import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;
import org.example.User.User;

public class UserTaskManager implements UTaskManager, URTaskManager {

    private final Server server;

    public UserTaskManager(Server server) {
        this.server = server;
    }

    @Override
    public void notifyNewUser(User user) {
        var id = user.getId();
        var username = user.getUsername();

        server.userManager.removeUser(id);
        server.userManager.active.addUser(id, user);
        server.notifier.notifyNewUser(id, username);
    }

    @Override
    public void broadcastMessage(int id, String username, String message) {
        server.messenger.broadcastToGroupUsers(id, username, message);
    }

    @Override
    public void notifyUserExit(int id, String username) {
        server.userManager.active.removeUser(id);
        server.notifier.notifyDisconnectedUser(id, username);
    }
}
