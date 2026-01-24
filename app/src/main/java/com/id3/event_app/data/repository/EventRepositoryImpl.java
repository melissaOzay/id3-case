package com.id3.event_app.data.repository;

import com.id3.event_app.data.mock.MockDataSource;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.model.EventFilter;
import com.id3.event_app.domain.repository.EventRepository;
import com.id3.event_app.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventRepositoryImpl implements EventRepository {

    private final MockDataSource mockDataSource;

    @Inject
    public EventRepositoryImpl() {
        this.mockDataSource = MockDataSource.getInstance();
    }

    @Override
    public List<Event> getEventsByFilter(EventFilter filter) {
        List<Event> allEvents = mockDataSource.getEvents();
        if (filter == EventFilter.ALL) {
            return allEvents;
        }

        List<Event> filteredEvents = new ArrayList<>();
        Date now = new Date();

        for (Event event : allEvents) {
            Date eventDate = event.getDate();
            if (eventDate == null) continue;

            switch (filter) {
                case TODAY:
                    if (DateUtils.isToday(eventDate)) {
                        filteredEvents.add(event);
                    }
                    break;
                case UPCOMING:
                    if (eventDate.after(now)) {
                        filteredEvents.add(event);
                    }
                    break;
                case PAST:
                    if (eventDate.before(now)) {
                        filteredEvents.add(event);
                    }
                    break;
                default:
                    break;
            }
        }
        return filteredEvents;
    }

}
