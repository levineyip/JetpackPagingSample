package com.an.paginglibrary.sample;

import android.app.Application;
import android.content.Context;

import com.an.paginglibrary.sample.rest.RestApi;
import com.an.paginglibrary.sample.rest.RestApiFactory;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class PagingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
