package com.example.nb_interview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.nb_interview.database.RoomDatabase;

public class BaseViewModel extends AndroidViewModel {

    protected RoomDatabase roomDatabase;
    public MutableLiveData<Boolean> isSuccess  = new MutableLiveData<>();
    public MutableLiveData<String> onError  = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading  = new MutableLiveData<>();


    public BaseViewModel(@NonNull Application application) {
        super(application);

        roomDatabase = RoomDatabase.getInstance(application);
    }
}
