package org.example.Server.Interfaces;


import org.example.Server.Configuration;

public abstract class Listener extends Configuration {
    protected abstract void startAsync();
}
