package com.id3.event_app.ui.event_detail.tabs.comments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.id3.event_app.core.base.BaseFragment;
import com.id3.event_app.data.model.Comment;
import com.id3.event_app.databinding.FragmentCommentsBinding;
import com.id3.event_app.ui.event_detail.tabs.adapter.CommentAdapter;
import com.id3.event_app.utils.ImageLoader;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CommentsFragment extends BaseFragment<FragmentCommentsBinding, CommentsViewModel> {

    private static final String ARG_EVENT_ID = "eventId";
    private CommentAdapter adapter;

    public static CommentsFragment newInstance(String eventId) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected FragmentCommentsBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCommentsBinding.inflate(inflater, container, false);
    }

    @Override
    protected Class<CommentsViewModel> getViewModelClass() {
        return CommentsViewModel.class;
    }

    @Override
    protected void initView() {
        setupRecyclerView();
        setupUserAvatar();
        setupKeyboardInsets();
    }

    private void setupKeyboardInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, windowInsets) -> {
            Insets imeInsets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            Insets systemBarInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            int bottomPadding = Math.max(imeInsets.bottom, systemBarInsets.bottom);
            int paddingPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5,
                    getResources().getDisplayMetrics()
            );

            binding.inputContainer.setPadding(
                    binding.inputContainer.getPaddingLeft(),
                    binding.inputContainer.getPaddingTop(),
                    binding.inputContainer.getPaddingRight(),
                    bottomPadding > 0 ? bottomPadding : paddingPx
            );

            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Override
    protected void initObservers() {
        viewModel.getComments().observe(getViewLifecycleOwner(), this::handleComments);
        viewModel.isSending().observe(getViewLifecycleOwner(), isSending ->
                binding.btnSend.setEnabled(!isSending));
        viewModel.getNewComment().observe(getViewLifecycleOwner(), comment -> {
            if (comment != null) {
                binding.editTextComment.setText("");
                binding.recyclerViewComments.smoothScrollToPosition(0);
                viewModel.onNewCommentHandled();
            }
        });
    }

    @Override
    protected void onLoadingChanged(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        if (isLoading) {
            binding.recyclerViewComments.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.GONE);
        }
    }

    private void handleComments(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            binding.recyclerViewComments.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewComments.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
            adapter.submitList(comments);
        }
    }

    @Override
    protected void initListeners() {
        binding.btnSend.setOnClickListener(v -> sendComment());
    }

    private void setupRecyclerView() {
        adapter = new CommentAdapter();
        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewComments.setAdapter(adapter);
    }

    private void setupUserAvatar() {
        ImageLoader.loadCircle(binding.userAvatar, "https://i.pravatar.cc/150?img=10");
    }

    private void sendComment() {
        String content = binding.editTextComment.getText().toString().trim();
        if (content.isEmpty()) return;
        viewModel.addComment(content);
    }

    @Override
    public void onDestroyView() {
        binding.recyclerViewComments.setAdapter(null);
        adapter = null;
        super.onDestroyView();
    }
}
