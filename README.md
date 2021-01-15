# JDABotsManager
Simple discord bots manager for JDA.<br>
By using this manager, you won't need to shade JDA into your bots anymore!<br>
The benefit of using it is to not have your files with size of 4mb+ due to JDA.<br>
Besides that, JDABotsManager has SQLite driver, gson & guava shaded into it as well.<br>

## How to use it?
The structure of the manager is similar to the way the Bukkit API works.<br>
Your main class should extend the DiscordBot class (Similar to the JavaPlugin class).<br>

Here is a basic example to a main class of a bot:
```java
public final class MyFirstBot extends DiscordBot {

    public MyFirstBot() throws LoginException, InterruptedException {
        super("<bot-token>");
    }
    
    @Override
    public void start() {
        // Start your code here. Use jda for a reference to the JDA instance.
    }

    @Override
    public void stop() {
       // Clear data & save things if you need.
    }
    
}
```

From this point, that's the same as developing a regular bot.<br>
You can get access to the JDA instance by using the `jda` field in DiscordBot.<br>
Furthermore, you get a folder for your bot where you can have all your files.<br>
You can get this folder by accessing the `dataFolder` field.<br>

All you have to do now is to build your bot, and put it in the bots folder that is generated on the first run of JDABotsManager.<br>
The manager will load all of your bots from the bots folder and call the start() method.<br>
There is no limit to the amount of bots that the manager can load at the same time.
