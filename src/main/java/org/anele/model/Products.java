package org.anele.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {

    public List<Product> products;

    public List<Product> getProducts() {
        return products;
    }
}
