package com.an.paginglibrary.sample.rest;

import com.an.paginglibrary.sample.bean.Feedback;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {


    @GET("/project/list/{page}/json")
    Call<Feedback> fetchFeed(@Path("page") int page, @Query("cid") int cid);
}
