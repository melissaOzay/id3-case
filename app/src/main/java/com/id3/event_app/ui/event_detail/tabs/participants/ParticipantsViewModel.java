package com.id3.event_app.ui.event_detail.tabs.participants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.id3.event_app.core.Resource;
import com.id3.event_app.core.base.BaseViewModel;
import com.id3.event_app.data.model.Participant;
import com.id3.event_app.domain.usecase.GetParticipantsUseCase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ParticipantsViewModel extends BaseViewModel {

    private static final String ARG_EVENT_ID = "eventId";

    private final GetParticipantsUseCase getParticipantsUseCase;
    private final SavedStateHandle savedStateHandle;

    private String searchQuery = "";

    private final MutableLiveData<List<Participant>> _participants = new MutableLiveData<>(Collections.emptyList());
    public LiveData<List<Participant>> getParticipants() {
        return _participants;
    }

    @Inject
    public ParticipantsViewModel(
            SavedStateHandle savedStateHandle,
            GetParticipantsUseCase getParticipantsUseCase
    ) {
        this.savedStateHandle = savedStateHandle;
        this.getParticipantsUseCase = getParticipantsUseCase;

        if (getEventId() != null) {
            loadParticipants();
        }
    }

    public String getEventId() {
        return savedStateHandle.get(ARG_EVENT_ID);
    }

    public void loadParticipants() {
        String eventId = getEventId();
        if (eventId == null) return;

        setLoading(true);
        clearError();

        Resource<List<Participant>> result;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            result = getParticipantsUseCase.search(eventId, searchQuery);
        } else {
            result = getParticipantsUseCase.execute(eventId);
        }

        setLoading(false);
        if (result.isSuccess()) {
            _participants.setValue(result.getData());
        } else {
            setError(result.getMessage());
        }
    }

    public void searchParticipants(String query) {
        this.searchQuery = query;
        loadParticipants();
    }
}
