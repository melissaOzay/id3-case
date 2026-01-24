package com.id3.event_app.domain.usecase;

import com.id3.event_app.core.Resource;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.model.EventFilter;
import com.id3.event_app.domain.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

public class GetEventsUseCase {

    private final EventRepository repository;

    @Inject
    public GetEventsUseCase(EventRepository repository) {
        this.repository = repository;
    }

    public Resource<List<Event>> execute(EventFilter filter) {
        try {
            List<Event> events = repository.getEventsByFilter(filter);
            if (events == null) {
                return Resource.error("Events not found");
            }
            return Resource.success(events);
        } catch (Exception e) {
            return Resource.error(e.getMessage() != null ? e.getMessage() : "Unknown error");
        }
    }
}
