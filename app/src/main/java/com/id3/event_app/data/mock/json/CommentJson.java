package com.id3.event_app.data.mock.json;

import java.util.List;

public class CommentJson {
    private String id;
    private String userId;
    private String userName;
    private String userAvatar;
    private String content;
    private int minutesAgo;
    private int likeCount;
    private boolean isLiked;
    private List<CommentJson> replies;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getContent() {
        return content;
    }

    public int getMinutesAgo() {
        return minutesAgo;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public List<CommentJson> getReplies() {
        return replies;
    }
}
