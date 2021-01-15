package com.ome_r.jdabotsmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;

public final class FileUtils {

    private FileUtils(){

    }

    @SuppressWarnings("deprecation")
    public static List<Class<?>> getClasses(File file, Class<?> clazz, ClassLoader classLoader){
        List<Class<?>> list = new ArrayList<>();

        URL url;

        try{
            url = file.toURL();
        }catch (MalformedURLException ex){
            return list;
        }

        try (URLClassLoader cl = new URLClassLoader(new URL[]{ url }, classLoader); JarInputStream jis = new JarInputStream(url.openStream())) {
            JarEntry jarEntry;
            while ((jarEntry = jis.getNextJarEntry()) != null){
                String name = jarEntry.getName();

                if (name == null || name.isEmpty() || !name.endsWith(".class")) {
                    continue;
                }

                name = name.replace("/", ".");
                String clazzName = name.substring(0, name.lastIndexOf(".class"));

                Class<?> c = cl.loadClass(clazzName);

                if (clazz.isAssignableFrom(c)) {
                    list.add(c);
                }
            }
        } catch (Throwable ignored) { }

        return list;
    }

    public static DiscordBot createBot(Class<?> clazz){
        if(!DiscordBot.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("Class " + clazz + " is not a valid bot.");

        for(Constructor<?> constructor : clazz.getConstructors()){
            if(constructor.getParameterCount() == 0) {
                if(!constructor.isAccessible())
                    constructor.setAccessible(true);

                try {
                    return (DiscordBot) constructor.newInstance();
                }catch (Exception ignored){ }
            }
        }

        throw new IllegalArgumentException("Class " + clazz + " has no valid constructors.");
    }

    public static void saveResource(DiscordBot discordBot, String resourcePath) {
        if(resourcePath == null || resourcePath.isEmpty())
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(discordBot.getClassLoader(), resourcePath);

        if (in == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + discordBot.getName());

        File outFile = new File(discordBot.getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf(47);
        File outDir = new File(discordBot.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            outDir.mkdirs();
        }

        try {
            OutputStream out = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];

            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (IOException ex) {
            JDABotsManager.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }

    }

    public static InputStream getResource(ClassLoader classLoader, String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        try {
            URL url = classLoader.getResource(filename);

            if (url == null)
                return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException var4) {
            return null;
        }
    }

}
