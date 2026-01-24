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
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

@AndroidEntryPoint
public class ParticipantsFragment extends BaseFragment<FragmentParticipantsBinding, ParticipantsViewModel> {

    private static final String ARG_EVENT_ID = "eventId";
    private static final long SEARCH_DEBOUNCE_DELAY = 1L;
    private ParticipantAdapter adapter;
    private final PublishSubject<String> searchSubject = PublishSubject.create();
    private Disposable searchDisposable;

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
        searchDisposable = searchSubject
                .debounce(SEARCH_DEBOUNCE_DELAY, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> viewModel.searchParticipants(query));

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
        }
    }

}
