package org.anele.utils;

public enum ApiPaths {

    SEARCH("/search"),
    ADD("/add");

    private final String path;

    ApiPaths(String path) {
        this.path = path;
    }

    public String get_path() {
        return path;
    }
}
