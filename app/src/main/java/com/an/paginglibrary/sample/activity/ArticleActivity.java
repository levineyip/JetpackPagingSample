package com.an.paginglibrary.sample.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.an.paginglibrary.sample.R;
import com.an.paginglibrary.sample.adapter.ArticleListAdapter;
import com.an.paginglibrary.sample.bean.Article;
import com.an.paginglibrary.sample.databinding.ArticleActivityBinding;
import com.an.paginglibrary.sample.utils.NetworkState;
import com.an.paginglibrary.sample.viewmodel.ArticleViewModel;


public class ArticleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ArticleListAdapter.OnItemClickListener {

    private ArticleListAdapter adapter;
    private ArticleViewModel articleViewModel;
    private ArticleActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Step 1: Using DataBinding, we setup the layout for the activity
         *
         * */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
         * Step 2: Initialize the ViewModel
         *
         * */
        articleViewModel = new ArticleViewModel();

        /*
         * Step 2: Setup the adapter class for the RecyclerView
         *
         * */
        binding.recylerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ArticleListAdapter(getApplicationContext());
        binding.recylerview.setAdapter(adapter);

        initEvent();
    }

    private Observer<NetworkState> networkObserver = networkState -> {
        adapter.setNetworkState(networkState);
    };
    private Observer<PagedList<Article>> articleObserver = pagedList -> {
        Log.d("Levine", "getArticleLiveData ：： " + pagedList);
        binding.swipeRefreshLayout.setRefreshing(false);
        adapter.submitList(pagedList);
    };

    private Observer<PagedList<Article>> refreshObserver = new Observer<PagedList<Article>>() {
        @Override
        public void onChanged(@Nullable PagedList<Article> listBeans) {
            binding.swipeRefreshLayout.setRefreshing(false);
            adapter.submitList(listBeans);
        }
    };

    private void initEvent() {
        /*
         * Step 4: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        articleViewModel.getArticleLiveData().observe(this, articleObserver);
        /*
         * Step 5: When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         *
         * */
        articleViewModel.getNetworkState().observe(this, networkObserver);


        binding.swipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        articleViewModel.getArticleLiveData().removeObserver(articleObserver);
        articleViewModel.getNetworkState().removeObserver(networkObserver);
    }

    @Override
    public void onRefresh() {
        adapter.submitList(null);
        articleViewModel.refreshData().observe(this, articleObserver);
    }

    @Override
    public void onItemClick(int position, Article item) {
        if (item != null) {
            Toast.makeText(this, "Article:: " + item.getLink(), Toast.LENGTH_SHORT).show();
        } else {
            /*Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();*/
            adapter.loadNext();
        }

    }
}
