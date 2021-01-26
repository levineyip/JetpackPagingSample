package com.an.paginglibrary.sample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.an.paginglibrary.sample.ArticleConstants;
import com.an.paginglibrary.sample.datasource.ArticleDataSource;
import com.an.paginglibrary.sample.datasource.factory.ArticleDataFactory;
import com.an.paginglibrary.sample.bean.Article;
import com.an.paginglibrary.sample.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ArticleViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Article>> articleLiveData;
    private ArticleDataFactory articleDataFactory;
    private PagedList.Config pagedListConfig;


    public ArticleViewModel() {
        init();
    }

    public void init() {
        executor = Executors.newFixedThreadPool(ArticleConstants.THREAD_COUNT);

        articleDataFactory = new ArticleDataFactory();
        networkState = Transformations.switchMap(articleDataFactory.getMutableLiveData(),
                ArticleDataSource::getNetworkState);

        pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(ArticleConstants.FIRST_PAGE)
                        /*.setPrefetchDistance(ArticleConstants.LOAD_NEXT_BORDER)*/
                        .setPageSize(ArticleConstants.PAGE_SIZE)
                        .build();

        articleLiveData = new LivePagedListBuilder(articleDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Article>> refreshData() {
        articleLiveData = new LivePagedListBuilder(articleDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();

        return articleLiveData;
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Article>> getArticleLiveData() {
        return articleLiveData;
    }

}
