package com.id3.event_app.data.mock;

import android.content.Context;

import com.id3.event_app.data.mock.json.CommentJson;
import com.id3.event_app.data.mock.json.EventJson;
import com.id3.event_app.data.mock.json.ParticipantJson;
import com.id3.event_app.data.model.Comment;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.data.model.LiveStatus;
import com.id3.event_app.data.model.Participant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MockDataSource {

    private static final String EVENTS_FILE = "mock_events.json";
    private static final String PARTICIPANTS_FILE = "mock_participants.json";
    private static final String COMMENTS_FILE = "mock_comments.json";
    private static final String STATUS_MESSAGES_FILE = "mock_status_messages.json";

    private static final String CURRENT_USER_ID = "current_user";
    private static final String CURRENT_USER_NAME = "You";
    private static final String CURRENT_USER_AVATAR = "https://i.pravatar.cc/150?img=10";

    private static final int STATUS_LIVE = 0;
    private static final int STATUS_ABOUT_TO_START = 1;
    private static final int STATUS_KEYNOTE = 2;
    private static final int STATUS_COFFEE = 3;
    private static final int STATUS_QA = 4;
    private static final int STATUS_NETWORKING = 5;
    private static final int STATUS_FINISHED = 6;

    private static volatile MockDataSource instance;

    private final List<Event> events = new ArrayList<>();
    private final Map<String, List<Participant>> eventParticipants = new HashMap<>();
    private final Map<String, List<Comment>> eventComments = new HashMap<>();
    private final Map<String, LiveStatus> eventLiveStatus = new HashMap<>();
    private final List<String> statusMessages = new ArrayList<>();
    private final Random random = new Random();

    private MockDataSource(Context context) {
        JsonDataLoader loader = new JsonDataLoader(context);
        loadEvents(loader);
        loadParticipants(loader);
        loadComments(loader);
        loadStatusMessages(loader);
        initializeLiveStatus();
    }

    public static MockDataSource getInstance(Context context) {
        if (instance == null) {
            synchronized (MockDataSource.class) {
                if (instance == null) {
                    instance = new MockDataSource(context);
                }
            }
        }
        return instance;
    }

    public static MockDataSource getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Error");
        }
        return instance;
    }

    private void loadEvents(JsonDataLoader loader) {
        List<EventJson> jsonEvents = loader.loadList(EVENTS_FILE, EventJson.class);
        Date now = new Date();

        for (EventJson json : jsonEvents) {
            Calendar eventDateCal = Calendar.getInstance();
            eventDateCal.setTime(now);
            eventDateCal.add(Calendar.DAY_OF_MONTH, json.getDaysOffset());

            Calendar createdAtCal = Calendar.getInstance();
            createdAtCal.setTime(now);
            createdAtCal.add(Calendar.HOUR_OF_DAY, -2);

            Event event = new Event(
                    json.getId(),
                    json.getTitle(),
                    json.getDescription(),
                    json.getImageUrl(),
                    eventDateCal.getTime(),
                    json.getLocation(),
                    json.isBookmarked(),
                    createdAtCal.getTime()
            );
            events.add(event);
        }
    }

    private void loadParticipants(JsonDataLoader loader) {
        List<ParticipantJson> jsonParticipants = loader.loadList(PARTICIPANTS_FILE, ParticipantJson.class);

        for (Event event : events) {
            List<Participant> participants = new ArrayList<>();
            for (ParticipantJson json : jsonParticipants) {
                Participant participant = new Participant(
                        json.getId() + "_" + event.getId(),
                        json.getName(),
                        json.getTitle(),
                        json.getCompany(),
                        json.getAvatarUrl(),
                        json.isSelected()
                );
                participants.add(participant);
            }
            eventParticipants.put(event.getId(), participants);
        }
    }

    private void loadComments(JsonDataLoader loader) {
        List<CommentJson> jsonComments = loader.loadList(COMMENTS_FILE, CommentJson.class);
        Date now = new Date();

        for (Event event : events) {
            List<Comment> comments = new ArrayList<>();
            for (CommentJson json : jsonComments) {
                Comment comment = createCommentFromJson(json, event.getId(), now);
                comments.add(comment);
            }
            eventComments.put(event.getId(), comments);
        }
    }

    private Comment createCommentFromJson(CommentJson json, String eventId, Date now) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, -json.getMinutesAgo());

        Comment comment = new Comment(
                json.getId() + "_" + eventId,
                eventId,
                json.getUserId(),
                json.getUserName(),
                json.getUserAvatar(),
                json.getContent(),
                cal.getTime(),
                json.getLikeCount(),
                json.isLiked()
        );


        return comment;
    }

    private void loadStatusMessages(JsonDataLoader loader) {
        List<String> messages = loader.loadStringList(STATUS_MESSAGES_FILE);
        statusMessages.addAll(messages);
    }

    private void initializeLiveStatus() {
        for (Event event : events) {
            String message = getStatusMessageByCurrentTime();
            boolean isLive = isLiveStatus(message);

            LiveStatus status = new LiveStatus(
                    UUID.randomUUID().toString(),
                    event.getId(),
                    message,
                    isLive,
                    new Date()
            );
            eventLiveStatus.put(event.getId(), status);
        }
    }

    private boolean isLiveStatus(String message) {
        String finishedMessage = statusMessages.get(STATUS_FINISHED);
        String aboutToStartMessage = statusMessages.get(STATUS_ABOUT_TO_START);
        return !message.equals(finishedMessage) && !message.equals(aboutToStartMessage);
    }

    private String getStatusMessageByCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int statusIndex;
        if (hour < 9) {
            statusIndex = STATUS_ABOUT_TO_START;
        } else if (hour == 9 && minute < 30) {
            statusIndex = STATUS_ABOUT_TO_START;
        } else if (hour >= 9 && hour < 10) {
            statusIndex = STATUS_KEYNOTE;
        } else if (hour >= 10 && hour < 11) {
            statusIndex = STATUS_QA;
        } else if (hour >= 11 && hour < 12) {
            statusIndex = STATUS_NETWORKING;
        } else if (hour >= 12 && hour < 13) {
            statusIndex = STATUS_COFFEE;
        } else if (hour >= 13 && hour < 15) {
            statusIndex = STATUS_KEYNOTE;
        } else if (hour >= 15 && hour < 16) {
            statusIndex = STATUS_QA;
        } else if (hour >= 16 && hour < 17) {
            statusIndex = STATUS_COFFEE;
        } else if (hour >= 17 && hour < 18) {
            statusIndex = STATUS_NETWORKING;
        } else if (hour >= 18 && hour < 19) {
            statusIndex = STATUS_LIVE;
        } else {
            statusIndex = STATUS_FINISHED;
        }

        return statusMessages.get(statusIndex);
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public Event getEventById(String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public List<Participant> getParticipantsByEventId(String eventId) {
        List<Participant> participants = eventParticipants.get(eventId);
        if (participants == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(participants);
    }

    public List<Participant> searchParticipants(String eventId, String query) {
        List<Participant> participants = eventParticipants.get(eventId);
        if (participants == null) {
            return new ArrayList<>();
        }

        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(participants);
        }

        String lowerQuery = query.toLowerCase().trim();
        List<Participant> filtered = new ArrayList<>();

        for (Participant participant : participants) {
            if (participant.getName().toLowerCase().contains(lowerQuery) ||
                    participant.getTitle().toLowerCase().contains(lowerQuery) ||
                    participant.getCompany().toLowerCase().contains(lowerQuery)) {
                filtered.add(participant);
            }
        }

        return filtered;
    }

    public List<Comment> getCommentsByEventId(String eventId) {
        List<Comment> comments = eventComments.get(eventId);
        if (comments == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(comments);
    }

    public Comment addComment(String eventId, String content) {
        List<Comment> comments = eventComments.get(eventId);
        if (comments == null) {
            comments = new ArrayList<>();
            eventComments.put(eventId, comments);
        }

        Comment newComment = new Comment(
                UUID.randomUUID().toString(),
                eventId,
                CURRENT_USER_ID,
                CURRENT_USER_NAME,
                CURRENT_USER_AVATAR,
                content,
                new Date(),
                0,
                false
        );

        comments.add(0, newComment);
        return newComment;
    }


    public LiveStatus getLiveStatusByEventId(String eventId) {
        return updateAndGetLiveStatus(eventId);
    }

    public LiveStatus refreshLiveStatus(String eventId) {
        return updateAndGetLiveStatus(eventId);
    }

    private LiveStatus updateAndGetLiveStatus(String eventId) {
        LiveStatus currentStatus = eventLiveStatus.get(eventId);
        if (currentStatus == null) {
            return null;
        }

        String message = getStatusMessageByCurrentTime();
        boolean isLive = isLiveStatus(message);

        LiveStatus updatedStatus = new LiveStatus(
                currentStatus.getId(),
                eventId,
                message,
                isLive,
                new Date()
        );

        eventLiveStatus.put(eventId, updatedStatus);
        return updatedStatus;
    }
}
