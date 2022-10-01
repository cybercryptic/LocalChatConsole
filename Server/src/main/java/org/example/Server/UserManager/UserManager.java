package org.example.Server.UserManager;

import org.example.User.User;

import java.util.Collection;

public class UserManager {
    private final ConnectedUsersManager connectedUsersManager = new ConnectedUsersManager();
    private final ActiveUsersManager activeUsersManager = new ActiveUsersManager();

    public void addConnectedUser(int id, User user) {
        connectedUsersManager.addUser(id, user);
    }

    public void addActiveUser(int id, User user) {
        activeUsersManager.addUser(id, user);
    }

    public void removeConnectedUser(int id) {
        connectedUsersManager.removeUser(id);
    }

    public void removeActiveUser(int id) {
        activeUsersManager.removeUser(id);
    }

    public User getConnectedUser(int id) {
        return connectedUsersManager.getUser(id);
    }

    public User getActiveUser(int id) {
        return activeUsersManager.getUser(id);
    }

    public Collection<User> getConnectedUsers() {
        return connectedUsersManager.getUsers();
    }

    public Collection<User> getActiveUsers() {
        return activeUsersManager.getUsers();
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

    public int getConnectedUsersSize() {
        return connectedUsersManager.usersSize();
    }

    public int getActiveUsersSize() {
        return activeUsersManager.usersSize();
    }

    public int getNoOfUsers() {
        return getConnectedUsersSize() + getActiveUsersSize();
    }
}
