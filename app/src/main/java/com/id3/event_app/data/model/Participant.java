package com.id3.event_app.data.model;

import java.util.Objects;

public class Participant {
    private String id;
    private final String name;
    private final String title;
    private final String company;
    private final String avatarUrl;
    private final boolean isSelected;

    public Participant(String id, String name, String title, String company, String avatarUrl, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.company = company;
        this.avatarUrl = avatarUrl;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFullTitle() {
        return title + " @ " + company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return isSelected == that.isSelected &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(title, that.title) &&
                Objects.equals(company, that.company) &&
                Objects.equals(avatarUrl, that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, company, avatarUrl, isSelected);
    }
}
