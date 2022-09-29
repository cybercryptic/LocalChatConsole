package org.example;

import org.example.Server.Messengers.MessageSenders.ServerBroadcaster;
import org.example.Server.Messengers.MessageSenders.ServerSender;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.UserManager.ActiveUserManager;
import org.example.Server.UserManager.UserManager;
import org.example.Server.UserTaskManager;
import org.example.User.Interfaces.URTaskManager;
import org.example.User.Interfaces.UTaskManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("org.example")
public class Config {

    private final UserManager manager = new UserManager();
    private final ActiveUserManager activeManager = new ActiveUserManager();
    private final ServerSender sender = new ServerSender(activeManager);
    private final ServerBroadcaster broadcaster = new ServerBroadcaster(activeManager);
    private final ServerMessenger messenger = new ServerMessenger(sender, broadcaster);
    private final ServerNotifier notifier = new ServerNotifier(broadcaster);
    private final UserTaskManager taskManager = new UserTaskManager(manager, activeManager, notifier, messenger);

    @Bean
    public UserManager userManager() {
        return manager;
    }

    @Bean
    public ActiveUserManager activeUserManager() {
        return activeManager;
    }

    @Bean
    public ServerSender serverSender() {
        return sender;
    }

    @Bean
    public ServerBroadcaster serverBroadcaster() {
        return broadcaster;
    }

    @Bean
    public ServerMessenger serverMessenger() {
        return messenger;
    }

    @Bean
    public ServerNotifier serverNotifier() {
        return notifier;
    }

    @Bean
    public UserTaskManager taskManager() {
        return taskManager;
    }

    @Bean
    public UTaskManager uTaskManager() {
        return taskManager;
    }

    @Bean
    public URTaskManager urTaskManager() {
        return taskManager;
    }
}
