package com.id3.event_app.domain.usecase;

import com.id3.event_app.core.Resource;
import com.id3.event_app.data.model.Participant;
import com.id3.event_app.domain.repository.EventDetailRepository;

import java.util.List;

import javax.inject.Inject;

public class GetParticipantsUseCase {
    private final EventDetailRepository repository;

    @Inject
    public GetParticipantsUseCase(EventDetailRepository repository) {
        this.repository = repository;
    }

    public Resource<List<Participant>> execute(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            return Resource.error("Event ID is required");
        }

        List<Participant> participants = repository.getParticipantsByEventId(eventId);
        if (participants == null) {
            return Resource.error("Failed to load participants");
        }
        return Resource.success(participants);
    }

    public Resource<List<Participant>> search(String eventId, String query) {
        if (eventId == null || eventId.isEmpty()) {
            return Resource.error("Event ID is required");
        }

        List<Participant> participants = repository.searchParticipants(eventId, query);
        if (participants == null) {
            return Resource.error("Search failed");
        }
        return Resource.success(participants);
    }
}
