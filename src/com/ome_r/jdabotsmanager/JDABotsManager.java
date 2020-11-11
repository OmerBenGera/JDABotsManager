package com.ome_r.jdabotsmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public final class JDABotsManager {

    private static final List<DiscordBot> discordBotList = new ArrayList<>();
    private static final Logger logger = Logger.getLogger("JDABotsManager");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> discordBotList.forEach(discordBot -> {
            logger.info("Disabling " + discordBot.getName());
            discordBot.stop();
        })));

        File botsFolder = new File("bots");

        if(!botsFolder.exists()) {
            botsFolder.mkdirs();
        }

        else for(File botFile : botsFolder.listFiles()){
            if(!botFile.getName().endsWith(".jar"))
                continue;

            BotClassLoader botClassLoader;

            try {
                botClassLoader = new BotClassLoader(botFile, DiscordBot.class.getClassLoader());
            }catch (Exception ex){
                logger.warning("Error while loading class loader:");
                ex.printStackTrace();
                continue;
            }

            Optional<Class<?>> discordBotClass = FileUtils.getClasses(botFile, DiscordBot.class, botClassLoader).stream().findFirst();

            if (!discordBotClass.isPresent()){
                logger.warning("The file " + botFile.getName() + " is not a valid bot jar.");
                continue;
            }

            DiscordBot discordBot = FileUtils.createBot(discordBotClass.get());
            discordBotList.add(discordBot);

            String botName = botFile.getName().replace(".jar", "");

            File dataFolder = new File(botsFolder, botName);

            if(!dataFolder.exists())
                dataFolder.mkdirs();

            discordBot.init(botName, dataFolder);

            logger.info("Enabling " + botName);

            discordBot.start();
        }
    }

}
