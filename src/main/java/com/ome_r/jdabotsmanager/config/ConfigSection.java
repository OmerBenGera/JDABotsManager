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

    public boolean contains(String path) {
        return get(path) != null;
    }

    public String getString(String path) {
        return getString(path, null);
    }

    public String getString(String path, String def) {
        Object value = get(path);
        return value == null ? def : value.toString();
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public int getInt(String path, int def) {
        Object value = get(path);
        return value instanceof Number ? ((Number) value).intValue() : def;
    }

    public boolean getBoolean(String path){
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean def){
        Object value = get(path);
        return value instanceof Boolean ? (Boolean) value : def;
    }

    public double getDouble(String path) {
        return getDouble(path, 0D);
    }

    public double getDouble(String path, double def) {
        Object value = get(path);
        return value instanceof Number ? ((Number) value).doubleValue() : def;
    }

    public float getFloat(String path) {
        return getFloat(path, 0f);
    }

    public float getFloat(String path, float def) {
        Object value = get(path);
        return value instanceof Number ? ((Number) value).floatValue() : def;
    }

    public long getLong(String path) {
        return getLong(path, 0L);
    }

    public long getLong(String path, long def) {
        Object value = get(path);
        return value instanceof Number ? ((Number) value).longValue() : def;
    }

    public List<String> getStringList(String path){
        Object value = get(path);
        //noinspection unchecked
        return value instanceof List ? (List<String>) value : new ArrayList<>();
    }

    public ConfigSection getSection(String path){
        Object value = get(path);
        //noinspection unchecked
        return value instanceof Map ? new ConfigSection((Map<String, Object>) value) : null;
    }

    public Set<String> getKeys(){
        return data.keySet();
    }

    private Object get(String path) {
        if (path.contains(".")) {
            String[] split = path.split("\\.");
            String otherPath = path.replace(split[0] + ".", "");
            path = split[0];
            Object object = data.get(path);
            if(!otherPath.isEmpty() && object instanceof Map)
                //noinspection unchecked
                return new ConfigSection((Map<String, Object>) object).get(otherPath);
        }

        return data.get(path);
    }

}
