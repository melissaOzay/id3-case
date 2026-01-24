package com.id3.event_app.domain.repository;

import com.id3.event_app.data.model.Event;
import com.id3.event_app.data.model.Participant;

import java.util.List;

public interface EventDetailRepository {
    Event getEventById(String eventId);

    List<Participant> getParticipantsByEventId(String eventId);

    List<Participant> searchParticipants(String eventId, String query);
}
