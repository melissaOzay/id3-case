package com.id3.event_app.ui.event_detail.tabs.live_status;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.id3.event_app.core.base.BaseViewModel;
import com.id3.event_app.data.model.LiveStatus;
import com.id3.event_app.domain.usecase.GetLiveStatusUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LiveStatusViewModel extends BaseViewModel {

    private static final String ARG_EVENT_ID = "eventId";
    private static final int COUNTDOWN_DURATION = 30;

    private final GetLiveStatusUseCase getLiveStatusUseCase;
    private final SavedStateHandle savedStateHandle;

    private Disposable timerDisposable;
    private int pausedAtSecond = COUNTDOWN_DURATION;
    private boolean shouldResumeFromPause = false;

    private final MutableLiveData<LiveStatus> _liveStatus = new MutableLiveData<>();

    public LiveData<LiveStatus> getLiveStatus() {
        return _liveStatus;
    }

    private final MutableLiveData<Integer> _countdownSeconds = new MutableLiveData<>(COUNTDOWN_DURATION);

    public LiveData<Integer> getCountdownSeconds() {
        return _countdownSeconds;
    }

    @Inject
    public LiveStatusViewModel(SavedStateHandle savedStateHandle, GetLiveStatusUseCase getLiveStatusUseCase) {
        this.savedStateHandle = savedStateHandle;
        this.getLiveStatusUseCase = getLiveStatusUseCase;

        if (getEventId() != null) {
            loadLiveStatus();
        }
    }

    public String getEventId() {
        return savedStateHandle.get(ARG_EVENT_ID);
    }

    public void loadLiveStatus() {
        String eventId = getEventId();
        if (eventId == null || eventId.isEmpty()) return;

        setLoading(true);
        clearError();

        LiveStatus status = getLiveStatusUseCase.execute(eventId);

        setLoading(false);
        if (status != null) {
            _liveStatus.setValue(status);
        } else {
            setError("Failed to load live status");
        }
    }

    public void refreshNow() {
        stopTimer();
        loadLiveStatus();
        startTimer(COUNTDOWN_DURATION);
    }

    public void resumeTimer() {
        if (isTimerRunning()) return;

        int startFrom = shouldResumeFromPause ? pausedAtSecond : COUNTDOWN_DURATION;
        shouldResumeFromPause = true;
        startTimer(startFrom);
    }

    public void pauseTimer() {
        disposeTimer();
    }

    public void stopTimer() {
        disposeTimer();
        pausedAtSecond = COUNTDOWN_DURATION;
        shouldResumeFromPause = false;
    }
    private void startTimer(int fromSeconds) {
        _countdownSeconds.setValue(fromSeconds);

        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(fromSeconds)
                .map(tick -> fromSeconds - 1 - tick.intValue())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onTick,
                        error -> {
                        },
                        this::onTimerComplete
                );
    }

    private void onTick(int secondsRemaining) {
        pausedAtSecond = secondsRemaining;
        _countdownSeconds.setValue(secondsRemaining);
    }

    private void onTimerComplete() {
        if (pausedAtSecond == 0) {
            refreshInBackground();
        }
    }

    private void refreshInBackground() {
        String eventId = getEventId();
        if (eventId == null) return;

        disposables.add(
                Observable.fromCallable(() -> getLiveStatusUseCase.refresh(eventId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                status -> {
                                    if (status != null) _liveStatus.setValue(status);
                                    startTimer(COUNTDOWN_DURATION);
                                },
                                error -> startTimer(COUNTDOWN_DURATION)
                        )
        );
    }

    private boolean isTimerRunning() {
        return timerDisposable != null && !timerDisposable.isDisposed();
    }

    private void disposeTimer() {
        if (isTimerRunning()) {
            timerDisposable.dispose();
        }
    }
}
