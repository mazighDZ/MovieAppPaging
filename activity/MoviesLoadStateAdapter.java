package com.section27.paginglibrarytest.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.section27.paginglibrarytest.R;
import com.section27.paginglibrarytest.databinding.LoadStateItemBinding;

public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.MyViewHolder> {
    private View.OnClickListener mRetryCallback;

    public MoviesLoadStateAdapter(View.OnClickListener mRetryCallback) {
        this.mRetryCallback = mRetryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesLoadStateAdapter.MyViewHolder myViewHolder, @NonNull LoadState loadState) {
        myViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public MoviesLoadStateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        return new MyViewHolder(parent,mRetryCallback);
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar mProgressBar;
        private TextView mErrorMsg;
        private Button mRetry;

        public MyViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.load_state_item , parent ,false
            ));

            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            mProgressBar = binding.progressBar;
            mErrorMsg=binding.tvErrorMsg;
            mRetry=binding.retryBtn;
            mRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState){
            if (loadState instanceof LoadState.Error){
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }

            mProgressBar.setVisibility(
                    loadState instanceof LoadState.Loading ?View.VISIBLE :View.GONE);

            mRetry.setVisibility(
                    loadState instanceof LoadState.Error ?View.VISIBLE :View.GONE);

            mErrorMsg.setVisibility(
                    loadState instanceof LoadState.Error ?View.VISIBLE :View.GONE);

        }


    }


}
