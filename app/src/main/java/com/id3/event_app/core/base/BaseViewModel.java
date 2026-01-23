package com.id3.event_app.core.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.id3.event_app.core.UiState;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<T> extends ViewModel {

    protected final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<UiState<T>> _uiState = new MutableLiveData<>(UiState.idle());

    public LiveData<UiState<T>> getUiState() {
        return _uiState;
    }

    protected void setLoading() {
        _uiState.setValue(UiState.loading());
    }

    protected void setSuccess(T data) {
        _uiState.setValue(UiState.success(data));
    }

    protected void setEmpty() {
        _uiState.setValue(UiState.empty());
    }

    protected void setError(String message) {
        _uiState.setValue(UiState.error(message));
    }

    protected void postSuccess(T data) {
        _uiState.postValue(UiState.success(data));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
