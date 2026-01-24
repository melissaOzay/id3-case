package com.id3.event_app.ui.event_detail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.id3.event_app.R;
import com.id3.event_app.data.model.Participant;
import com.id3.event_app.databinding.ItemParticipantBinding;
import com.id3.event_app.utils.ImageLoader;

public class ParticipantAdapter extends ListAdapter<Participant, ParticipantAdapter.ViewHolder> {

    public ParticipantAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Participant> DIFF_CALLBACK = new DiffUtil.ItemCallback<Participant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Participant oldItem, @NonNull Participant newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Participant oldItem, @NonNull Participant newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemParticipantBinding binding = ItemParticipantBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemParticipantBinding binding;

        ViewHolder(ItemParticipantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Participant participant) {
            binding.participantName.setText(participant.getName());
            binding.participantTitle.setText(participant.getFullTitle());

            ImageLoader.loadCircle(binding.participantAvatar, participant.getAvatarUrl(), R.drawable.ic_empty_events);

        }
    }

}
