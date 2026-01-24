package com.id3.event_app.ui.event_detail.tabs.comments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.id3.event_app.core.base.BaseViewModel;
import com.id3.event_app.data.model.Comment;
import com.id3.event_app.domain.usecase.AddCommentUseCase;
import com.id3.event_app.domain.usecase.GetCommentsUseCase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CommentsViewModel extends BaseViewModel {

    private static final String ARG_EVENT_ID = "eventId";

    private final GetCommentsUseCase getCommentsUseCase;
    private final AddCommentUseCase addCommentUseCase;
    private final SavedStateHandle savedStateHandle;

    private final MutableLiveData<List<Comment>> _comments = new MutableLiveData<>(Collections.emptyList());
    public LiveData<List<Comment>> getComments() {
        return _comments;
    }

    private final MutableLiveData<Boolean> _isSending = new MutableLiveData<>(false);
    public LiveData<Boolean> isSending() {
        return _isSending;
    }

    private final MutableLiveData<Comment> _newComment = new MutableLiveData<>();
    public LiveData<Comment> getNewComment() {
        return _newComment;
    }

    @Inject
    public CommentsViewModel(
            SavedStateHandle savedStateHandle,
            GetCommentsUseCase getCommentsUseCase,
            AddCommentUseCase addCommentUseCase
    ) {
        this.savedStateHandle = savedStateHandle;
        this.getCommentsUseCase = getCommentsUseCase;
        this.addCommentUseCase = addCommentUseCase;

        if (getEventId() != null) {
            loadComments();
        }
    }

    public String getEventId() {
        return savedStateHandle.get(ARG_EVENT_ID);
    }

    public void loadComments() {
        String eventId = getEventId();
        if (eventId == null || eventId.isEmpty()) {
            return;
        }

        setLoading(true);
        clearError();

        List<Comment> comments = getCommentsUseCase.execute(eventId);

        setLoading(false);
        _comments.setValue(comments);
    }

    public void addComment(String content) {
        String eventId = getEventId();
        if (eventId == null || content == null || content.trim().isEmpty()) {
            return;
        }

        _isSending.setValue(true);

        Comment newComment = addCommentUseCase.execute(eventId, content.trim());

        _isSending.setValue(false);
        if (newComment != null) {
            _newComment.setValue(newComment);
            loadComments();
        } else {
            setError("Failed to add comment");
        }
    }

    public void onNewCommentHandled() {
        _newComment.setValue(null);
    }
}
