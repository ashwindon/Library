package com.example.ashwin.library;

public class BookInfo {
    private String Title;
    private String Author;
    private String SubGenre;
    private String Genre;
    private String qty;
    private String Publisher;
    // private String image;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getSubGenre() {
        return SubGenre;
    }

    public void setSubGenre(String subGenre) {
        SubGenre = subGenre;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }
}
