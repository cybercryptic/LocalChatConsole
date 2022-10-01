package org.example.User.Interfaces;

import org.example.User.Main.User;

public interface UserHandler {
    void notifyNewUser(User user);
    void notifyUserExit(User user);
}
