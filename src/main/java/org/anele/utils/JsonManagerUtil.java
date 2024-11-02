package org.anele.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.anele.model.Product;


import java.io.File;
import java.io.IOException;

public class JsonManagerUtil {

    public synchronized File load_json_schema(String file_name) {
        return new File("src/test/resources/" + file_name);
    }

    public synchronized Product ready_products(String file) {

        try {
            //load file to used
            File product_file = load_json_schema(file);
            //check if file is not null
            if (product_file == null) {
                throw new IllegalArgumentException("File could not be loaded: " + file);
            }
            //json parser to serialize/deserialize product object
            ObjectMapper mapper = new ObjectMapper();
            // return single product from json file
            return mapper.readValue(product_file, Product.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read products from file: " + file, e);
        }
    }
}
