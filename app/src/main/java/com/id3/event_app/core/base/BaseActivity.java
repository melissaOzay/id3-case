package com.id3.event_app.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public abstract class BaseActivity<VB extends ViewBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VB binding;
    protected VM viewModel;
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(getViewModelClass());

        initView();
        initObservers();
        initListeners();
    }


    protected abstract VB getViewBinding(LayoutInflater inflater);


    protected abstract Class<VM> getViewModelClass();


    protected abstract void initView();


    protected abstract void initObservers();


    protected abstract void initListeners();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
        binding = null;
    }
}
