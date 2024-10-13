package org.anele.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileManager {
    //define a private properties object
    protected Properties properties;

    public PropertyFileManager() {
        properties = new Properties();
        loadProperties();
    }

    public synchronized void loadProperties() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (stream == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //get property values
    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getPath() {
        return properties.getProperty("base.path");
    }
}
