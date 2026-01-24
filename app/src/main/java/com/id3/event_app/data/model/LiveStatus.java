package com.id3.event_app.data.model;

import java.util.Date;
import java.util.Objects;

public class LiveStatus {
    private String id;
    private String eventId;
    private final String statusMessage;
    private final boolean isLive;

    public LiveStatus(String id, String eventId, String statusMessage, boolean isLive, Date lastUpdated) {
        this.id = id;
        this.eventId = eventId;
        this.statusMessage = statusMessage;
        this.isLive = isLive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveStatus that = (LiveStatus) o;
        return isLive == that.isLive &&
                Objects.equals(id, that.id) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(statusMessage, that.statusMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, statusMessage, isLive);
    }
}
