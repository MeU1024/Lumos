package com.example.lumos.ui.collection;

import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lumos.DBOpenHelper;
import com.example.lumos.Habit;

import java.util.ArrayList;
import java.util.List;

public class CollectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;



    public CollectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}