package com.id3.event_app.data.repository;

import com.id3.event_app.data.mock.MockDataSource;
import com.id3.event_app.data.model.Comment;
import com.id3.event_app.domain.repository.CommentRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommentRepositoryImpl implements CommentRepository {
    private final MockDataSource mockDataSource;

    @Inject
    public CommentRepositoryImpl() {
        this.mockDataSource = MockDataSource.getInstance();
    }

    @Override
    public List<Comment> getCommentsByEventId(String eventId) {
        return mockDataSource.getCommentsByEventId(eventId);
    }

    @Override
    public Comment addComment(String eventId, String content) {
        return mockDataSource.addComment(eventId, content);
    }

}
