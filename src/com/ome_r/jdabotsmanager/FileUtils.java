package com.ome_r.jdabotsmanager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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

}
