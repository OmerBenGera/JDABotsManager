package com.ome_r.jdabotsmanager.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ConfigSection {

    private final Map<String, Object> data;

    ConfigSection(Map<String, Object> data){
        this.data = data;
    }

    public long getLong(String path){
        return get(path, Long.class);
    }

    public String getString(String path){
        return get(path, String.class);
    }

    public ConfigSection getSection(String path){
        //noinspection all
        return new ConfigSection(get(path, Map.class));
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String path){
        return get(path, ArrayList.class);
    }

    public Set<String> getKeys(){
        return data.keySet();
    }

    private <T> T get(String path, Class<T> type) {
        if (path.contains(".")) {
            String[] split = path.split("\\.");
            String otherPath = path.replace(split[0] + ".", "");
            path = split[0];
            Object object = data.get(path);
            if(!otherPath.isEmpty() && object instanceof Map)
                //noinspection unchecked
                return new ConfigSection((Map<String, Object>) object).get(otherPath, type);
        }

        Object value = data.get(path);
        return value == null ? null : type.cast(value);
    }

}
