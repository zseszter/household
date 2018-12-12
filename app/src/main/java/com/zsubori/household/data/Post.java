package com.zsubori.household.data;

public class Post {
    private String uid;
    private String author;
    private String body;


    public Post() {
    }

    public Post(String uid, String author, String body) {
        this.uid = uid;
        this.author = author;
        this.body = body;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}