package com.ome_r.jdabotsmanager.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

public final class ConfigParser extends Yaml {

    private final ConfigSection defaultSection;

    public ConfigParser(File file) throws FileNotFoundException {
        defaultSection = new ConfigSection(load(new FileReader(file)));
    }

    public boolean contains(String path) {
        return defaultSection.contains(path);
    }

    public String getString(String path) {
        return defaultSection.getString(path);
    }

    public String getString(String path, String def) {
        return defaultSection.getString(path, def);
    }

    public int getInt(String path) {
        return defaultSection.getInt(path);
    }

    public int getInt(String path, int def) {
        return defaultSection.getInt(path, def);
    }

    public boolean getBoolean(String path){
        return defaultSection.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def){
        return defaultSection.getBoolean(path, def);
    }

    public double getDouble(String path) {
        return defaultSection.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return defaultSection.getDouble(path, def);
    }

    public float getFloat(String path) {
        return defaultSection.getFloat(path);
    }

    public float getFloat(String path, float def) {
        return defaultSection.getFloat(path, def);
    }

    public long getLong(String path) {
        return defaultSection.getLong(path);
    }

    public long getLong(String path, long def) {
        return defaultSection.getLong(path, def);
    }

    public List<String> getStringList(String path){
        return defaultSection.getStringList(path);
    }

    public ConfigSection getSection(String path){
        return defaultSection.getSection(path);
    }

    public Set<String> getKeys(){
        return defaultSection.getKeys();
    }

}
