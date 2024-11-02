package org.anele.utils;
import java.io.File;

public class JsonManagerUtil {

    public synchronized File load_json_schema(String file_name) {
        return new File("src/test/resources/" + file_name);
    }
}
