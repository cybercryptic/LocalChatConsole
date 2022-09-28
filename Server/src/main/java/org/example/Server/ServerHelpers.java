package org.example.Server;

import org.example.Server.UserManager.UserManager;

public abstract class ServerHelpers {
    public UserManager userManager;
    public ServerBroadcaster broadcaster;
    public ServerNotifier notifier;
    public ServerSender sender;
    public UserTaskManager userTaskManager;
    protected ServerListener listener;
    protected ServerWriter writer;
    protected ActionCenter actionCenter;
    public final static Console console = new Console();
}
