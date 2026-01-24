package com.id3.event_app.domain.usecase;

import com.id3.event_app.data.model.Comment;
import com.id3.event_app.domain.repository.CommentRepository;

import java.util.List;

import javax.inject.Inject;

public class GetCommentsUseCase {
    private final CommentRepository repository;

    @Inject
    public GetCommentsUseCase(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> execute(String eventId) {
        return repository.getCommentsByEventId(eventId);
    }
}
