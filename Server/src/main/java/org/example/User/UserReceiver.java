package org.example.User;

import org.example.User.Interfaces.URTaskManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserReceiver {

    private final User user;
    private final int id;
    private final URTaskManager taskManager;

    public UserReceiver(User user, URTaskManager URTaskManager) {
        this.user = user;
        id = user.getId();
        this.taskManager = URTaskManager;
    }

    public void startAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void start() throws IOException {
        var dis = getDis();

        var username = dis.readUTF();
        setUsernameNNotify(username);

        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                user.stop();
                break;
            }
            taskManager.broadcastMessage(id, username, message);
        }

        dis.close();
    }

    private void setUsernameNNotify(String username) {
        user.setUsername(username);
        taskManager.notifyNewUser(id, username);
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(user.getSocket().getInputStream());
    }
}
