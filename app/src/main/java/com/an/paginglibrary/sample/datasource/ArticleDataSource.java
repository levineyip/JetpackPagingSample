package com.an.paginglibrary.sample.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.an.paginglibrary.sample.ArticleConstants;
import com.an.paginglibrary.sample.bean.Article;
import com.an.paginglibrary.sample.bean.Feedback;
import com.an.paginglibrary.sample.rest.RestApiFactory;
import com.an.paginglibrary.sample.utils.NetworkState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleDataSource extends PageKeyedDataSource<Integer, Article>  {

    private static final String TAG = ArticleDataSource.class.getSimpleName();

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    public ArticleDataSource() {
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }


    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Article> callback) {
        Log.i(TAG, "loadInitial Count " + params.requestedLoadSize);

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        RestApiFactory.getInstance().fetchFeed(ArticleConstants.FIRST_PAGE)
                .enqueue(new Callback<Feedback>() {
                    @Override
                    public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                        if (response.isSuccessful()) {
                            callback.onResult(response.body().getData().getDatas(), null, ArticleConstants.SECOND_PAGE);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Feedback> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Article> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        RestApiFactory.getInstance().fetchFeed(params.key)
                .enqueue(new Callback<Feedback>() {
                    @Override
                    public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                        if (response.isSuccessful()) {
                            int nextKey = (params.key == response.body().getData().getTotal()) ? null : params.key + 1;
                            callback.onResult(response.body().getData().getDatas(), nextKey);
                            networkState.postValue(NetworkState.LOADED);
                        } else
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                    @Override
                    public void onFailure(Call<Feedback> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }
}
