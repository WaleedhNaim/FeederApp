package com.example.nb_interview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.nb_interview.database.entity.User;

public class SplashViewModel extends BaseViewModel {


    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    /*
    Method to check if there is any logged in user
     */
    public void isLogged() {
        User user = roomDatabase.userDao().getCurrentUser();
        isSuccess.postValue(user != null);
    }

}
