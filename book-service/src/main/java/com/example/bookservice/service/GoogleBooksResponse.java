package com.example.bookservice.service;

import java.util.List;

public class GoogleBooksResponse {
    public List<Item> items;

    public static class Item {
        public VolumeInfo volumeInfo;
    }

    public static class VolumeInfo {
        public String title;
        public List<String> authors;
        public String description;
        public List<String> categories;
        public String previewLink;
    }
}
