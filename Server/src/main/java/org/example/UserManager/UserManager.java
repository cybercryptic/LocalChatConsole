package org.example.UserManager;

import org.example.User.Main.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserManager {
    private final ConnectedUsersManager connectedUsersManager;
    private final ActiveUsersManager activeUsersManager;
    private final AtomicInteger noOfUsers = new AtomicInteger();

    public UserManager(ConnectedUsersManager connectedUsersManager, ActiveUsersManager activeUsersManager) {
        this.connectedUsersManager = connectedUsersManager;
        this.activeUsersManager = activeUsersManager;
    }

    public void addConnectedUser(int id, User user) {
        connectedUsersManager.addUser(id, user);
    }

    public boolean isConnectedUserWith(int id) {
        return connectedUsersManager.containsId(id);
    }

    public boolean isActiveUserWith(int id) {
        return activeUsersManager.containsId(id);
    }

    public boolean containsId(int id) {
        return isConnectedUserWith(id) && isActiveUserWith(id);
    }

    public AtomicInteger getConnectedUsersSize() {
        return connectedUsersManager.usersSize();
    }

    public AtomicInteger getActiveUsersSize() {
        return activeUsersManager.usersSize();
    }

    public AtomicInteger getNoOfUsers() {
        int size = getConnectedUsersSize().get() + getActiveUsersSize().get();
        noOfUsers.set(size);
        return noOfUsers;
    }
}
