package com.section27.paginglibrarytest.api;

import static com.section27.paginglibrarytest.utils.Utils.BASE_URL;

import com.section27.paginglibrarytest.model.Movie;
import com.section27.paginglibrarytest.model.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ApiClient {
static  MovieApiService movieApiService;

    public static MovieApiService getMovieApiService() {

        if (movieApiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            movieApiService = retrofit.create(MovieApiService.class);
        }


        return movieApiService;
    }

    public interface MovieApiService {
        @GET("movie/popular")
        Single<List<Movie>> getMovies(@Query("api_key") String apiKey,@Query("page") int page);  @GET("movie/popular")
        Single<MovieResponse> getMoviesByPage(@Query("api_key") String apiKey, @Query("page") int page);
    }
}
