package org.example.Server.Main;

import org.example.Server.ActionCenter;
import org.example.Server.Messengers.MessageSenders.ServerBroadcaster;
import org.example.Server.Messengers.ServerMessenger;
import org.example.Server.Messengers.ServerNotifier;
import org.example.Server.Messengers.MessageSenders.ServerSender;
import org.example.Server.UserManager.UserManager;
import org.example.Server.UserTaskManager;

public abstract class ServerHelpers {
    public UserManager userManager;
    public ServerBroadcaster broadcaster;
    public ServerSender sender;
    public ServerNotifier notifier;
    public ServerMessenger messenger;
    public UserTaskManager userTaskManager;
    protected ServerListener listener;
    protected ServerWriter writer;
    protected ActionCenter actionCenter;
    public final static Console console = new Console();
}
