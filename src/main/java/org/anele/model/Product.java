package org.anele.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    public int id;
    public String title;
    public String description;
    public String category;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public ArrayList<String> tags;
    public String brand;
    public String sku;
    public int weight;
    public Dimensions dimensions;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public ArrayList<Review> reviews;
    public String returnPolicy;
    public int minimumOrderQuantity;
    public Meta meta;
    public ArrayList<String> images;
    public String thumbnail;
}
