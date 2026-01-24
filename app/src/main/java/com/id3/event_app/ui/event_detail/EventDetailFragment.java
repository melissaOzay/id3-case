package com.id3.event_app.ui.event_detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.tabs.TabLayoutMediator;
import com.id3.event_app.core.base.BaseFragment;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.databinding.FragmentEventDetailBinding;
import com.id3.event_app.utils.DateUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventDetailFragment extends BaseFragment<FragmentEventDetailBinding, EventDetailViewModel> {

    @Override
    protected FragmentEventDetailBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentEventDetailBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<EventDetailViewModel> getViewModelClass() {
        return EventDetailViewModel.class;
    }

    @Override
    protected void initView() {
        setupViewPager();
    }

    @Override
    protected void initObservers() {
        viewModel.getEvent().observe(getViewLifecycleOwner(), this::bindEventData);
    }

    @Override
    protected void onLoadingChanged(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initListeners() {
        binding.toolbar.setNavigationOnClickListener(v ->
                Navigation.findNavController(binding.getRoot()).navigateUp());
    }

    private void setupViewPager() {
        String eventId = viewModel.getEventId();
        EventDetailPagerAdapter pagerAdapter = new EventDetailPagerAdapter(this, eventId);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(getString(EventDetailPagerAdapter.TAB_TITLES[position]))
        ).attach();
    }

    private void bindEventData(Event event) {
        if (event == null) return;

        binding.eventTitle.setText(event.getTitle());
        binding.eventLocation.setText(event.getLocation());

        if (event.getDate() != null) {
            binding.eventDate.setText(DateUtils.formatEventDate(event.getDate()));
        }

        if (event.getImageUrl() != null && !event.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(event.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(binding.headerImage);
        }
    }
}
