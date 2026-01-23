package com.id3.event_app.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;
import com.id3.event_app.core.UiState;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseFragment<VB extends ViewBinding, VM extends BaseViewModel<?>> extends Fragment {

    protected VB binding;
    protected VM viewModel;
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());

        initView();
        observeUiState();
        initObservers();
        initListeners();
    }

    protected abstract VB getViewBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract Class<VM> getViewModelClass();

    protected abstract void initView();

    protected abstract void initObservers();

    protected abstract void initListeners();

    private void observeUiState() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), this::handleUiState);
    }

    protected void handleUiState(UiState<?> state) {
        if (state.isLoading()) {
            onLoading();
        } else if (state.isSuccess()) {
            onSuccess(state.getData());
        } else if (state.isEmpty()) {
            onEmpty();
        } else if (state.isError()) {
            onError(state.getErrorMessage());
        } else if (state.isIdle()) {
            onIdle();
        }
    }

    protected void onIdle() {
    }

    protected void onLoading() {
    }

    protected void onSuccess(Object data) {
    }

    protected void onEmpty() {
    }

    protected void onError(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showError(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        binding = null;
    }
}
