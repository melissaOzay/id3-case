package com.id3.event_app.data.repository;

import com.id3.event_app.data.mock.MockDataSource;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.data.model.Participant;
import com.id3.event_app.domain.repository.EventDetailRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventDetailRepositoryImpl implements EventDetailRepository {
    private final MockDataSource mockDataSource;

    @Inject
    public EventDetailRepositoryImpl() {
        this.mockDataSource = MockDataSource.getInstance();
    }

    @Override
    public Event getEventById(String eventId) {
        return mockDataSource.getEventById(eventId);
    }

    @Override
    public List<Participant> getParticipantsByEventId(String eventId) {
        return mockDataSource.getParticipantsByEventId(eventId);
    }

    @Override
    public List<Participant> searchParticipants(String eventId, String query) {
        return mockDataSource.searchParticipants(eventId, query);
    }

}
