package com.id3.event_app.core.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    protected final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    protected void setLoading(boolean loading) {
        _isLoading.setValue(loading);
    }

    protected void setError(String message) {
        _errorMessage.setValue(message);
    }

    protected void clearError() {
        _errorMessage.setValue(null);
    }

    public void onErrorShown() {
        clearError();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
