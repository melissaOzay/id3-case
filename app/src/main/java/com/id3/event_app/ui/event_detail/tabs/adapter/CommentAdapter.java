package com.id3.event_app.ui.event_detail.tabs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.id3.event_app.R;
import com.id3.event_app.data.model.Comment;
import com.id3.event_app.databinding.ItemCommentBinding;
import com.id3.event_app.utils.DateUtils;

public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.ViewHolder> {

    public CommentAdapter() {
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
        ItemCommentBinding binding = ItemCommentBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding binding;
        private final CommentReplyAdapter replyAdapter;

        ViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.replyAdapter = new CommentReplyAdapter();
            binding.recyclerViewReplies.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
            binding.recyclerViewReplies.setAdapter(replyAdapter);
        }

        void bind(Comment comment) {
            binding.userName.setText(comment.getUserName());
            binding.commentContent.setText(comment.getContent());
            binding.commentTime.setText(DateUtils.getRelativeTime(comment.getTimestamp()));

            if (comment.getLikeCount() > 0) {
                binding.likeCount.setText(String.valueOf(comment.getLikeCount()));
                binding.likeCount.setVisibility(View.VISIBLE);
            } else {
                binding.likeCount.setVisibility(View.GONE);
            }

            if (comment.isLiked()) {
                binding.iconLike.setImageResource(R.drawable.ic_like_filled);
            } else {
                binding.iconLike.setImageResource(R.drawable.ic_like);
            }

            Glide.with(binding.userAvatar.getContext())
                    .load(comment.getUserAvatarUrl())
                    .circleCrop()
                    .into(binding.userAvatar);

            if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
                binding.recyclerViewReplies.setVisibility(View.VISIBLE);
                replyAdapter.submitList(comment.getReplies());
            } else {
                binding.recyclerViewReplies.setVisibility(View.GONE);
            }

        }

    }
}
