package com.sneakerate.web.domain;

public class Story {
    private String name;
    private String title;
    private String keywords;
    private String description;

    public Story(String name, String title, String keywords, String description) {
        this.name = name;
        this.title = title;
        this.keywords = keywords;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean validate() {
        if (name == null || title == null || keywords == null || description == null) {
            return false;
        }

        return true;
    }
}
