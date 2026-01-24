package com.id3.event_app.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Comment {
    private String id;
    private String eventId;
    private final String userId;
    private final String userName;
    private final String userAvatarUrl;
    private String content;
    private final Date timestamp;
    private final int likeCount;
    private final boolean isLiked;
    private final List<Comment> replies;

    public Comment(String id, String eventId, String userId, String userName, String userAvatarUrl,
                   String content, Date timestamp, int likeCount, boolean isLiked) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
        this.content = content;
        this.timestamp = timestamp;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.replies = new ArrayList<>();
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

    public String getUserName() {
        return userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public List<Comment> getReplies() {
        return replies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return likeCount == comment.likeCount &&
                isLiked == comment.isLiked &&
                Objects.equals(id, comment.id) &&
                Objects.equals(eventId, comment.eventId) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(userName, comment.userName) &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, userId, userName, content, likeCount, isLiked);
    }
}
