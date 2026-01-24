package com.id3.event_app.domain.repository;

import com.id3.event_app.data.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getCommentsByEventId(String eventId);

    Comment addComment(String eventId, String content);
}
