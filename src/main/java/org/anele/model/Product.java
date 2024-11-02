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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public double getRating() {
        return rating;
    }

    public int getStock() {
        return stock;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getBrand() {
        return brand;
    }

    public String getSku() {
        return sku;
    }

    public int getWeight() {
        return weight;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public String getWarrantyInformation() {
        return warrantyInformation;
    }

    public String getShippingInformation() {
        return shippingInformation;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public int getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public Meta getMeta() {
        return meta;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
