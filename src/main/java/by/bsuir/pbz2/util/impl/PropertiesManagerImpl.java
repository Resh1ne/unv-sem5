package by.bsuir.pbz2.util.impl;

import by.bsuir.pbz2.util.PropertiesManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManagerImpl implements PropertiesManager {
    private final Properties properties;

    public PropertiesManagerImpl(String fileName) {
        try (InputStream inputStream = getClass().getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKey(String key) {
        return properties.getProperty(key);
    }
}
