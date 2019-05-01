package com.lizxing.daily.items;

public class QuotesItem {

    private String content;
    private String author;

    public QuotesItem(String content, String author){
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
