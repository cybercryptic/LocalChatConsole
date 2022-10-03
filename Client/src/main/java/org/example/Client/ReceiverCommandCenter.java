package org.example.Client;

public class ReceiverCommandCenter {

    private final Client client;

    public ReceiverCommandCenter(Client client) {
        this.client = client;
    }

    public void execute(String rsvMsg) {
        if (rsvMsg.equals("stop")) client.getSession().set(false);
        else Client.console.print(rsvMsg);
    }
}
