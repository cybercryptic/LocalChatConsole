package org.example;

import org.example.Server.Communicators.ServerBroadcaster;
import org.example.Server.Communicators.ServerMessenger;
import org.example.Server.Communicators.ServerNotifier;
import org.example.Server.Communicators.ServerSender;
import org.example.User.Interfaces.URHandler;
import org.example.User.Interfaces.UserHandler;
import org.example.User.UserHandlerImpl;
import org.example.UserManager.ActiveUsersManager;
import org.example.UserManager.ConnectedUsersManager;
import org.example.UserManager.UserManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("org.example")
public class Config {

    private final ConnectedUsersManager connectedUsersManager = new ConnectedUsersManager();
    private final ActiveUsersManager activeUsersManager = new ActiveUsersManager();
    private final UserManager userManager = new UserManager(connectedUsersManager, activeUsersManager);
    private final ServerSender sender = new ServerSender(activeUsersManager);
    private final ServerBroadcaster broadcaster = new ServerBroadcaster(activeUsersManager);
    private final ServerMessenger messenger = new ServerMessenger(sender, broadcaster);
    private final ServerNotifier notifier = new ServerNotifier(broadcaster);
    private final UserHandlerImpl taskManager = new UserHandlerImpl(connectedUsersManager, activeUsersManager, notifier, messenger);

    @Bean
    public ConnectedUsersManager connectedUsersManager() {
        return connectedUsersManager;
    }

    @Bean
    public ActiveUsersManager activeUsersManager() {
        return activeUsersManager;
    }

    @Bean
    public UserManager userManager() {
        return userManager;
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
    public UserHandlerImpl userHandlerImpl() {
        return taskManager;
    }

    @Bean
    public UserHandler userHandler() {
        return taskManager;
    }

    @Bean
    public URHandler urHandler() {
        return taskManager;
    }
}
