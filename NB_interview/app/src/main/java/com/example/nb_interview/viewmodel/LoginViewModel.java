package com.example.nb_interview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.nb_interview.database.entity.User;

public class LoginViewModel extends BaseViewModel {


    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(final String username, String password) {

        isLoading.postValue(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000); // fake delay for show loading bar
                    isLoading.postValue(false);
                    saveUserdata(username);
                    isSuccess.postValue(true);
                } catch (Exception e) {
                    isLoading.postValue(false);
                    onError.postValue(e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /*
    Method to save the current login user
     */
    private void saveUserdata(String username) {
        User user = new User();
        user.setUserName(username);
        roomDatabase.userDao().save(user);
    }

}
