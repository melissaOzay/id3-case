package com.id3.event_app.domain.usecase;

import com.id3.event_app.data.model.Comment;
import com.id3.event_app.domain.repository.CommentRepository;

import javax.inject.Inject;

public class AddCommentUseCase {
    private final CommentRepository repository;

    @Inject
    public AddCommentUseCase(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment execute(String eventId, String content) {
        return repository.addComment(eventId, content);
    }
}
