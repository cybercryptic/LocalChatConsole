package org.example.Server;

import org.example.User.UserTaskManager;

public abstract class Configuration {
    public UserManager userManager;
    protected ServerListener listener;
    protected ServerWriter writer;
    public ServerSender sender;
    protected ActionCenter actionCenter;
    public ServerBroadcaster broadcaster;
    public ServerNotifier notifier;
    public UserTaskManager userTaskManager;
    public final static Console console = new Console();
}
