package com.blog.myblogapp;

public class Blog {

    private String title;
    private String desc;
    private long id;

    public Blog() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Blog(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }
}
