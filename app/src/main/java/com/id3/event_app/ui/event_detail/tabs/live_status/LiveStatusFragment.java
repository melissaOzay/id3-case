package com.id3.event_app.ui.event_detail.tabs.live_status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.id3.event_app.R;
import com.id3.event_app.core.base.BaseFragment;
import com.id3.event_app.data.model.LiveStatus;
import com.id3.event_app.databinding.FragmentLiveStatusBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LiveStatusFragment extends BaseFragment<FragmentLiveStatusBinding, LiveStatusViewModel> {

    private static final String ARG_EVENT_ID = "eventId";

    public static LiveStatusFragment newInstance(String eventId) {
        LiveStatusFragment fragment = new LiveStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FragmentLiveStatusBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLiveStatusBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<LiveStatusViewModel> getViewModelClass() {
        return LiveStatusViewModel.class;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initObservers() {
        viewModel.getLiveStatus().observe(getViewLifecycleOwner(), this::handleLiveStatus);
        viewModel.getCountdownSeconds().observe(getViewLifecycleOwner(), seconds ->
                binding.tvCountdown.setText(getString(R.string.next_update_in, seconds)));
    }

    @Override
    protected void onLoadingChanged(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (isLoading) {
            binding.contentContainer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void showError(String message) {
        super.showError(message);
        binding.contentContainer.setVisibility(View.GONE);
    }

    private void handleLiveStatus(LiveStatus status) {
        if (status != null) {
            binding.contentContainer.setVisibility(View.VISIBLE);
            binding.tvStatusMessage.setText(status.getStatusMessage());
        }
    }

    @Override
    protected void initListeners() {
        binding.btnRefresh.setOnClickListener(v -> viewModel.refreshNow());
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.resumeTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.pauseTimer();
    }
}
