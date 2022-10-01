package org.example.Server;

import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.UserManager.ActiveUsersManager;
import org.example.Server.UserManager.ConnectedUsersManager;
import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;
import org.example.User.User;

public class UserTaskManager implements UTaskManager, URTaskManager {

    private final ConnectedUsersManager connectedUsersManager;
    private final ActiveUsersManager activeUsersManager;
    private final ServerNotifier notifier;
    private final ServerMessenger messenger;

    public UserTaskManager(ConnectedUsersManager connectedUsersManager, ActiveUsersManager activeUsersManager, ServerNotifier notifier,
                           ServerMessenger messenger) {
        this.connectedUsersManager = connectedUsersManager;
        this.activeUsersManager = activeUsersManager;
        this.notifier = notifier;
        this.messenger = messenger;
    }

    @Override
    public void notifyNewUser(User user) {
        var id = user.getId();
        var username = user.getUsername();

        connectedUsersManager.removeUser(id);
        activeUsersManager.addUser(id, user);
        notifier.notifyNewUser(id, username);
    }

    @Override
    public void broadcastMessage(int id, String username, String message) {
        messenger.broadcastToGroupUsers(id, username, message);
    }

    @Override
    public void notifyUserExit(User user) {
        activeUsersManager.removeUser(user.getId());
        notifier.notifyDisconnectedUser(user.getId(), user.getUsername());
    }
}
