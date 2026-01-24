package com.id3.event_app.domain.usecase;

import com.id3.event_app.data.model.LiveStatus;
import com.id3.event_app.domain.repository.LiveStatusRepository;

import javax.inject.Inject;

public class GetLiveStatusUseCase {
    private final LiveStatusRepository repository;

    @Inject
    public GetLiveStatusUseCase(LiveStatusRepository repository) {
        this.repository = repository;
    }

    public LiveStatus execute(String eventId) {
        return repository.getLiveStatusByEventId(eventId);
    }

    public LiveStatus refresh(String eventId) {
        return repository.refreshLiveStatus(eventId);
    }
}
