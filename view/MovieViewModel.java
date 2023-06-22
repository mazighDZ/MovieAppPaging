package com.section27.paginglibrarytest.view;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.section27.paginglibrarytest.model.Movie;
import com.section27.paginglibrarytest.paging.MoviePagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class MovieViewModel extends ViewModel {

    public Flowable<PagingData<Movie>> moviePagingDataFlowable;

    public MovieViewModel() {

    init();
    }

    public void init(){
        // Define Paging Source
        MoviePagingSource moviePagingSource = new MoviePagingSource();
        PagingConfig pagingConfig =new PagingConfig(20,20,false,20,20*499);

        Pager<Integer ,Movie> pager = new Pager(pagingConfig,()->moviePagingSource);
        // Flowable
        moviePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(moviePagingDataFlowable, coroutineScope);


    }

}
