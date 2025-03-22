package com.example.project_ui_implementation.model;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private List<Item> items;

    public List<Books> getBooks() {
        List<Books> books = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                String title = item.volumeInfo.title;
                String author = (item.volumeInfo.authors != null && !item.volumeInfo.authors.isEmpty()) ? item.volumeInfo.authors.get(0) : "Unknown Author";
                String thumbnail = (item.volumeInfo.imageLinks != null) ? item.volumeInfo.imageLinks.thumbnail : null;
                String genre = item.volumeInfo.genre;
                books.add(new Books(title, author, genre, thumbnail));
            }
        }
        return books;
    }

    private static class Item {
        VolumeInfo volumeInfo;
    }

    private static class VolumeInfo {
        String title;
        List<String> authors;
        ImageLinks imageLinks;
        //synopsis
        //genre
        String genre;
    }

    private static class ImageLinks {
        String thumbnail;
    }
}
