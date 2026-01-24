package com.id3.event_app.data.repository;

import com.id3.event_app.data.mock.MockDataSource;
import com.id3.event_app.data.model.LiveStatus;
import com.id3.event_app.domain.repository.LiveStatusRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LiveStatusRepositoryImpl implements LiveStatusRepository {
    private final MockDataSource mockDataSource;

    @Inject
    public LiveStatusRepositoryImpl() {
        this.mockDataSource = MockDataSource.getInstance();
    }

    @Override
    public LiveStatus getLiveStatusByEventId(String eventId) {
        return mockDataSource.getLiveStatusByEventId(eventId);
    }

    @Override
    public LiveStatus refreshLiveStatus(String eventId) {
        return mockDataSource.refreshLiveStatus(eventId);
    }
}
