package com.id3.event_app.domain.repository;

import com.id3.event_app.data.model.LiveStatus;

public interface LiveStatusRepository {
    LiveStatus getLiveStatusByEventId(String eventId);

    LiveStatus refreshLiveStatus(String eventId);
}
