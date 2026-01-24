package com.id3.event_app.data.model;

import java.util.Date;

public class Event {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Date date;
    private String location;
    private boolean isBookmarked;
    private Date createdAt;

    public Event() {
    }

    public Event(String id, String title, String description, String imageUrl,
                 Date date, String location, boolean isBookmarked, Date createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
        this.location = location;
        this.isBookmarked = isBookmarked;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
