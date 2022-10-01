package org.example.User;

import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Communicators.ServerNotifier;
import org.example.User.Interfaces.URHandler;
import org.example.User.Main.User;
import org.example.UserManager.ActiveUsersManager;
import org.example.UserManager.ConnectedUsersManager;

public class UserHandlerImpl implements org.example.User.Interfaces.UserHandler, URHandler {

    private final ConnectedUsersManager connectedUsersManager;
    private final ActiveUsersManager activeUsersManager;
    private final ServerNotifier notifier;
    private final ServerMessenger messenger;

    public UserHandlerImpl(ConnectedUsersManager connectedUsersManager, ActiveUsersManager activeUsersManager, ServerNotifier notifier,
                           ServerMessenger messenger) {
        this.connectedUsersManager = connectedUsersManager;
        this.activeUsersManager = activeUsersManager;
        this.notifier = notifier;
        this.messenger = messenger;
    }

    @Override
    public void notifyNewUser(User user) {
        var id = user.getId();
        connectedUsersManager.removeUser(id);
        activeUsersManager.addUser(id, user);
        notifier.notifyNewUser(user);
    }

    @Override
    public void broadcastToGroupUsers(int id, String username, String message) {
        messenger.broadcastToGroupUsers(id, username, message);
    }

    @Override
    public void notifyUserExit(User user) {
        activeUsersManager.removeUser(user.getId());
        notifier.notifyDisconnectedUser(user);
    }
}
