package com.section27.paginglibrarytest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.section27.paginglibrarytest.adapter.MoviesAdapter;
import com.section27.paginglibrarytest.databinding.ActivityMainBinding;
import com.section27.paginglibrarytest.utils.GridSpace;
import com.section27.paginglibrarytest.utils.MovieComparator;
import com.section27.paginglibrarytest.utils.Utils;
import com.section27.paginglibrarytest.view.MovieViewModel;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;
    RecyclerView recyclerView;
@Inject
    RequestManager requestManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
// setup binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()){
            Toast.makeText(this, "Error in API Key", Toast.LENGTH_SHORT).show();
        }
        //replace this code with injection dependency Module
//        RequestManager requestManager = Glide.with(this)
//                .applyDefaultRequestOptions(new RequestOptions()
//                        .error(R.drawable.ic_image)
//                        .placeholder(R.drawable.ic_image));


        // adapter
         moviesAdapter = new MoviesAdapter(new MovieComparator(),requestManager);
        //viewModel
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // ini Recyclerview and Adapter
        initRecyclerviewAndAdapter();

        // subscribe to paging data
        //sets up a subscription to the moviePagingDataFlowable. It defines an action to be performed whenever new data is emitted by the observable.
//       //Inside the subscription, moviesAdapter.submitData(getLifecycle(), moviePagingData) is
//       called. This method is provided by the PagingDataAdapter class. It is used to submit new data to the adapter and update the UI.
//moviePagingData is the new data emitted by the observable. It represents a page of movies in this case.
        //By calling moviesAdapter.submitData(getLifecycle(), moviePagingData),
        // the adapter is notified about the new data. The adapter internally handles
        // the diffing process to calculate the differences between the new data and
        // the existing data and efficiently update the RecyclerView accordingly.
        mainActivityViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });
    }

    private void initRecyclerviewAndAdapter() {
    recyclerView = binding.recyclerViewMovies;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new GridSpace(2,20,true));
//if we want add our costumer load state
    //recyclerView.setAdapter(moviesAdapter);

        // here we add
    recyclerView.setAdapter(moviesAdapter.withLoadStateFooter(
            new MoviesLoadStateAdapter(view->{
                moviesAdapter.retry();
            })
    ));

    // we change griLayout if item Loading to Grid 1
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1:2;
            }
        });
        }
}