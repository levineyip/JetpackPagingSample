package com.an.paginglibrary.sample.datasource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.an.paginglibrary.sample.datasource.ArticleDataSource;
import com.an.paginglibrary.sample.bean.Article;

public class ArticleDataFactory extends DataSource.Factory<Integer, Article> {

    private MutableLiveData<ArticleDataSource> mutableLiveData;
    private ArticleDataSource articleDataSource;

    public ArticleDataFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        articleDataSource = new ArticleDataSource();
        mutableLiveData.postValue(articleDataSource);
        return articleDataSource;
    }


    public MutableLiveData<ArticleDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
