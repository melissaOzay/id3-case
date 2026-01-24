package com.id3.event_app.ui.event_detail.tabs.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.id3.event_app.data.model.Comment;
import com.id3.event_app.databinding.ItemCommentReplyBinding;
import com.id3.event_app.utils.DateUtils;
import com.id3.event_app.utils.ImageLoader;

public class CommentReplyAdapter extends ListAdapter<Comment, CommentReplyAdapter.ViewHolder> {

    public CommentReplyAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Comment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Comment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentReplyBinding binding = ItemCommentReplyBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentReplyBinding binding;

        ViewHolder(ItemCommentReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Comment reply) {
            binding.userName.setText(reply.getUserName());
            binding.commentContent.setText(reply.getContent());
            binding.commentTime.setText(DateUtils.getRelativeTime(reply.getTimestamp()));

            ImageLoader.loadCircle(binding.userAvatar, reply.getUserAvatarUrl());
        }
    }
}
