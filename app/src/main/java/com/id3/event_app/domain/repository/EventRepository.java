package com.id3.event_app.domain.repository;

import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.model.EventFilter;

import java.util.List;

public interface EventRepository {
    List<Event> getEventsByFilter(EventFilter filter);
}
