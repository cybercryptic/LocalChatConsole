package org.example.Server;

import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.UserManager.ActiveUserManager;
import org.example.Server.UserManager.UserManager;
import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;
import org.example.User.User;

public class UserTaskManager implements UTaskManager, URTaskManager {

    private final UserManager userManager;
    private final ActiveUserManager activeUserManager;
    private final ServerNotifier notifier;
    private final ServerMessenger messenger;

    public UserTaskManager(UserManager userManager, ActiveUserManager activeUserManager, ServerNotifier notifier,
                           ServerMessenger messenger) {
        this.userManager = userManager;
        this.activeUserManager = activeUserManager;
        this.notifier = notifier;
        this.messenger = messenger;
    }

    @Override
    public void notifyNewUser(User user) {
        var id = user.getId();
        var username = user.getUsername();

        userManager.removeUser(id);
        activeUserManager.addUser(id, user);
        notifier.notifyNewUser(id, username);
    }

    @Override
    public void broadcastMessage(int id, String username, String message) {
        messenger.broadcastToGroupUsers(id, username, message);
    }

    @Override
    public void notifyUserExit(int id, String username) {
        activeUserManager.removeUser(id);
        notifier.notifyDisconnectedUser(id, username);
    }
}
