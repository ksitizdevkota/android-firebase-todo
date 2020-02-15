package com.ksitiz.todoapp;

import android.app.Application;

import com.ksitiz.todoapp.di.ApplicationComponent;
import com.ksitiz.todoapp.di.ApplicationModule;
import com.ksitiz.todoapp.di.DaggerApplicationComponent;

public class TodoApplication extends Application {

    public ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }
}
