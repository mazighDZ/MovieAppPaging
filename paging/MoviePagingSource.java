package com.section27.paginglibrarytest.paging;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.section27.paginglibrarytest.api.ApiClient;
import com.section27.paginglibrarytest.model.Movie;
import com.section27.paginglibrarytest.model.MovieResponse;
import com.section27.paginglibrarytest.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource< Integer , Movie> {
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return null;
    }


    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try {
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            // fetch data from apiClient

          return   ApiClient.getMovieApiService()
                    .getMoviesByPage(Utils.API_KEY, page)
                    .subscribeOn(Schedulers.io())
                    .map(MovieResponse::getMovies)
                    .map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);

        } catch (Exception e) {
            return Single.just(new LoadResult.Error<>(e));
        }

    }
    private LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page){
        return new LoadResult.Page(movies,page ==1 ? null : page -1 , page+1);
    }
}
