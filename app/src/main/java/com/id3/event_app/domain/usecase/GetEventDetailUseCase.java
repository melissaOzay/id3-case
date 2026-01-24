package com.id3.event_app.domain.usecase;

import com.id3.event_app.core.Resource;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.repository.EventDetailRepository;

import javax.inject.Inject;

public class GetEventDetailUseCase {
    private final EventDetailRepository repository;

    @Inject
    public GetEventDetailUseCase(EventDetailRepository repository) {
        this.repository = repository;
    }

    public Resource<Event> execute(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            return Resource.error("Event ID is required");
        }

        Event event = repository.getEventById(eventId);
        if (event == null) {
            return Resource.error("Event not found");
        }
        return Resource.success(event);
    }
}
