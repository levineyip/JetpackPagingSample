package com.an.paginglibrary.sample.rest;

import android.util.Log;

import com.an.paginglibrary.sample.ArticleConstants;
import com.an.paginglibrary.sample.bean.Feedback;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiFactory {

    private static final String TAG = RestApiFactory.class.getSimpleName();
    private RestApi apiService;

    private static class Wrapper {
        static RestApiFactory httpService = new RestApiFactory();
    }

    private RestApiFactory() {
        mBodyLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(mBodyLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ArticleConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(RestApi.class);
    }

    public static RestApiFactory getInstance() {
        return Wrapper.httpService;
    }

    private static final HttpLoggingInterceptor mBodyLoggingInterceptor = new HttpLoggingInterceptor(message -> {
        Log.d(TAG, "mBodyLoggingInterceptor::body=" + message);
    });

    public Call<Feedback> fetchFeed(int page) {
        return apiService.fetchFeed(page, ArticleConstants.CID);
    }
}
