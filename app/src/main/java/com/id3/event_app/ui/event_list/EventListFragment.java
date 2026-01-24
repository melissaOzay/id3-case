package com.id3.event_app.ui.event_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.id3.event_app.R;
import com.id3.event_app.core.base.BaseFragment;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.databinding.FragmentEventListBinding;
import com.id3.event_app.domain.model.EventFilter;
import com.id3.event_app.ui.event_list.adapter.EventAdapter;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventListFragment extends BaseFragment<FragmentEventListBinding, EventListViewModel> {

    private EventAdapter adapter;

    @Override
    protected FragmentEventListBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEventListBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<EventListViewModel> getViewModelClass() {
        return EventListViewModel.class;
    }

    @Override
    protected void initView() {
        setupRecyclerView();
        setupChipGroup();
    }

    @Override
    protected void initObservers() {
        viewModel.getEvents().observe(getViewLifecycleOwner(), this::handleEvents);
        viewModel.getCurrentFilter().observe(getViewLifecycleOwner(), this::updateChipSelection);
    }

    @Override
    protected void onLoadingChanged(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (isLoading) {
            binding.recyclerViewEvents.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.GONE);
        }
    }

    private void handleEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            binding.recyclerViewEvents.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewEvents.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
            adapter.submitList(events);
        }
    }

    private void navigateToEventDetail(String eventId) {
        Bundle args = new Bundle();
        args.putString("eventId", eventId);
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_eventList_to_eventDetail, args);
    }

    @Override
    protected void initListeners() {}

    private void setupRecyclerView() {
        adapter = new EventAdapter();
        adapter.setOnEventClickListener(event -> navigateToEventDetail(event.getId()));
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewEvents.setAdapter(adapter);
    }

    private void setupChipGroup() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            int checkedId = checkedIds.get(0);
            EventFilter filter = EventFilter.ALL;

            if (checkedId == R.id.chipAll) {
                filter = EventFilter.ALL;
            } else if (checkedId == R.id.chipToday) {
                filter = EventFilter.TODAY;
            } else if (checkedId == R.id.chipUpcoming) {
                filter = EventFilter.UPCOMING;
            } else if (checkedId == R.id.chipPast) {
                filter = EventFilter.PAST;
            }

            viewModel.setFilter(filter);
        });
    }

    private void updateChipSelection(EventFilter filter) {
        if (filter == null) return;

        switch (filter) {
            case ALL:
                binding.chipAll.setChecked(true);
                break;
            case TODAY:
                binding.chipToday.setChecked(true);
                break;
            case UPCOMING:
                binding.chipUpcoming.setChecked(true);
                break;
            case PAST:
                binding.chipPast.setChecked(true);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        binding.recyclerViewEvents.setAdapter(null);
        adapter = null;
        super.onDestroyView();
    }
}
