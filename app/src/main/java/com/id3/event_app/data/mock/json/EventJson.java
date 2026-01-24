package com.id3.event_app.data.mock.json;

public class EventJson {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String location;
    private int daysOffset;
    private boolean isBookmarked;

    public String getId() {
        return id;
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

    public String getLocation() {
        return location;
    }

    public int getDaysOffset() {
        return daysOffset;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }
}
