package com.id3.event_app.ui.event_list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.id3.event_app.R;
import com.id3.event_app.data.model.Event;
import com.id3.event_app.databinding.ItemEventBinding;
import com.id3.event_app.utils.DateUtils;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    private OnEventClickListener onEventClickListener;

    public EventAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.isBookmarked() == newItem.isBookmarked();
        }
    };

    public void setOnEventClickListener(OnEventClickListener listener) {
        this.onEventClickListener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final ItemEventBinding binding;

        EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Event event) {
            binding.textTitle.setText(event.getTitle());
            binding.textDescription.setText(event.getDescription());

            if (event.getCreatedAt() != null) {
                binding.textTime.setText(DateUtils.getRelativeTime(event.getCreatedAt()));
            }

            if (event.getDate() != null) {
                binding.textDate.setText(DateUtils.formatEventDate(event.getDate()));
            }

            int bookmarkIcon = event.isBookmarked() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_outline;
            binding.buttonBookmark.setImageResource(bookmarkIcon);

            Glide.with(binding.imageEvent.getContext())
                    .load(event.getImageUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(binding.imageEvent);

            binding.cardEvent.setOnClickListener(v -> {
                if (onEventClickListener != null) {
                    onEventClickListener.onEventClick(event);
                }
            });
        }
    }

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

}
