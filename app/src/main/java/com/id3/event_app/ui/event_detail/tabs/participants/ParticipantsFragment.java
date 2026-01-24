package com.id3.event_app.ui.event_detail.tabs.participants;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.id3.event_app.core.base.BaseFragment;
import com.id3.event_app.data.model.Participant;
import com.id3.event_app.databinding.FragmentParticipantsBinding;
import com.id3.event_app.ui.event_detail.adapter.ParticipantAdapter;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ParticipantsFragment extends BaseFragment<FragmentParticipantsBinding, ParticipantsViewModel> {

    private static final String ARG_EVENT_ID = "eventId";
    private ParticipantAdapter adapter;

    public static ParticipantsFragment newInstance(String eventId) {
        ParticipantsFragment fragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FragmentParticipantsBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentParticipantsBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<ParticipantsViewModel> getViewModelClass() {
        return ParticipantsViewModel.class;
    }

    @Override
    protected void initView() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new ParticipantAdapter();
        binding.participantsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.participantsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initObservers() {
        viewModel.getParticipants().observe(getViewLifecycleOwner(), this::handleParticipants);
    }

    @Override
    protected void onLoadingChanged(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (isLoading) {
            binding.participantsRecyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.GONE);
        }
    }

    private void handleParticipants(List<Participant> participants) {
        if (participants == null || participants.isEmpty()) {
            binding.participantsRecyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.participantsRecyclerView.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
            adapter.submitList(participants);
        }
    }

    @Override
    protected void initListeners() {
        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchParticipants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

}
