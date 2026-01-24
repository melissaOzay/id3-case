package com.id3.event_app.ui.event_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.id3.event_app.core.Resource;
import com.id3.event_app.core.base.BaseViewModel;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.domain.model.EventFilter;
import com.id3.event_app.domain.usecase.GetEventsUseCase;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EventListViewModel extends BaseViewModel {

    private final GetEventsUseCase getEventsUseCase;

    private final MutableLiveData<List<Event>> _events = new MutableLiveData<>(Collections.emptyList());
    public LiveData<List<Event>> getEvents() {
        return _events;
    }

    private final MutableLiveData<EventFilter> _currentFilter = new MutableLiveData<>(EventFilter.ALL);
    public LiveData<EventFilter> getCurrentFilter() {
        return _currentFilter;
    }

    @Inject
    public EventListViewModel(GetEventsUseCase getEventsUseCase) {
        this.getEventsUseCase = getEventsUseCase;
        loadEvents();
    }

    public void loadEvents() {
        EventFilter filter = _currentFilter.getValue();
        if (filter == null) filter = EventFilter.ALL;
        loadEventsByFilter(filter);
    }

    public void setFilter(EventFilter filter) {
        _currentFilter.setValue(filter);
        loadEventsByFilter(filter);
    }

    private void loadEventsByFilter(EventFilter filter) {
        setLoading(true);
        clearError();

        Resource<List<Event>> result = getEventsUseCase.execute(filter);

        setLoading(false);
        if (result.isSuccess()) {
            _events.setValue(result.getData());
        } else {
            setError(result.getMessage());
        }
    }
}
