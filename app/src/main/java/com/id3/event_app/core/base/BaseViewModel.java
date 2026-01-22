package com.id3.event_app.core.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    protected final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public MutableLiveData<String> error = _error;

    protected void setLoading(boolean loading) {
        _isLoading.postValue(loading);
    }

    protected void setError(String message) {
        _error.postValue(message);
    }

    protected void clearError() {
        _error.postValue(null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
