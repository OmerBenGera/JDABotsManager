package com.ome_r.jdabotsmanager.config;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public final class ConfigParser extends Yaml {

    private final ConfigSection defaultSection;

    public ConfigParser(File file) throws FileNotFoundException {
        defaultSection = new ConfigSection(load(new FileReader(file)));
    }

    public String getString(String path){
        return defaultSection.getString(path);
    }

    public ConfigSection getSection(String path){
        return defaultSection.getSection(path);
    }

    public List<String> getStringList(String path){
        return defaultSection.getStringList(path);
    }

}
