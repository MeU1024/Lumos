package com.example.lumos.ui.info;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class InfoViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> loginStatus;

    public InfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Info fragment");

        loginStatus = new MutableLiveData<>();
        loginStatus.setValue(Boolean.FALSE);
    }

    public LiveData<String> getText() {
        return mText;
    }

}