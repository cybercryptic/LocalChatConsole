package org.example.User;

import org.example.User.Interfaces.URTaskManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class UserReceiver {

    private final User user;
    private final int id;
    private final DataInputStream dis;
    private final URTaskManager taskManager;

    public UserReceiver(User user, URTaskManager URTaskManager) throws IOException {
        this.user = user;
        id = user.getId();
        dis = getDis();

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
        var message = "";
        while (true) {
            message = dis.readUTF();
            if (message.equals("stop")) {
                user.stop();
                break;
            }
            taskManager.broadcastMessage(id, user.getUsername(), message);
        }

        dis.close();
    }

    public String getUsername() throws IOException {
        return dis.readUTF();
    }

    private DataInputStream getDis() throws IOException {
        return new DataInputStream(user.getSocket().getInputStream());
    }
}
