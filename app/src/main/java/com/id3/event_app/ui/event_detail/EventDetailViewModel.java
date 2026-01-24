package com.id3.event_app.ui.event_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.id3.event_app.core.Resource;
import com.id3.event_app.core.base.BaseViewModel;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.usecase.GetEventDetailUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EventDetailViewModel extends BaseViewModel {

    private static final String KEY_EVENT_ID = "eventId";

    private final GetEventDetailUseCase getEventDetailUseCase;
    private final SavedStateHandle savedStateHandle;

    private final MutableLiveData<Event> _event = new MutableLiveData<>();
    public LiveData<Event> getEvent() {
        return _event;
    }

    @Inject
    public EventDetailViewModel(
            SavedStateHandle savedStateHandle,
            GetEventDetailUseCase getEventDetailUseCase
    ) {
        this.savedStateHandle = savedStateHandle;
        this.getEventDetailUseCase = getEventDetailUseCase;

        if (getEventId() != null) {
            loadEventDetail();
        }
    }

    public String getEventId() {
        return savedStateHandle.get(KEY_EVENT_ID);
    }

    public void loadEventDetail() {
        String eventId = getEventId();
        if (eventId == null) return;

        setLoading(true);
        clearError();

        Resource<Event> result = getEventDetailUseCase.execute(eventId);

        setLoading(false);
        if (result.isSuccess()) {
            _event.setValue(result.getData());
        } else {
            setError(result.getMessage());
        }
    }
}
