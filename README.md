# Local Chat Console Application V2
Server can connect to multiple clients and responsively send & receive messages.
## How to run
1. Download [java 18](https://www.oracle.com/java/technologies/downloads/) and install.
2. Download [Server](https://github.com/cybercryptic/LocalChatConsoleApplication/releases/download/Stable/Server.jar) and start it.
> java -jar Server.jar [Port] [Server capacity]
3. Download [Client](https://github.com/cybercryptic/LocalChatConsoleApplication/releases/download/Stable/Client.jar) and start it.
> java -jar Client.jar [Server host] [Port]
## How to use
#### Server
```
To send message
-u [id] [message]
Ex: -u 32 hi
To stop
-c [command]
Ex: -c stop
```
#### Client
```
To send message
Just type....
To stop
type.. stop
```
Only few commands supported for now.
###### Note: If you stop server, all clients will stop.
## Dependencies
JDK 18 is used
