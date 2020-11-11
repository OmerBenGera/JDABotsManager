package com.ome_r.jdabotsmanager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.EnumSet;

public abstract class DiscordBot {

    protected final JDA jda;

    protected String name;
    protected File dataFolder;

    protected DiscordBot(String token) throws LoginException, InterruptedException {
        this.jda = JDABuilder.create(token, EnumSet.allOf(GatewayIntent.class)).build().awaitReady();
    }

    public final JDA getJDA(){
        return jda;
    }

    public String getName() {
        return name;
    }

    public void init(String name, File dataFolder){
        this.name = name;
        this.dataFolder = dataFolder;
    }

    public File getDataFolder(){
        return dataFolder;
    }

    public abstract void start();

    public abstract void stop();

}
