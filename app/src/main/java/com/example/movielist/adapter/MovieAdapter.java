package com.example.movielist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movielist.R;
import com.example.movielist.model.Search;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Search> mData;
    private LayoutInflater mInflater;

    public MovieAdapter(Context context, ArrayList<Search> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Search movie = mData.get(position);
        holder.movieName.setText(movie.getTitle());
        holder.movieYear.setText(movie.getYear());

        Glide.with(holder.movieImg)
                .load(movie.getPoster())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.movieImg);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName,movieYear;
        ImageView movieImg;

        ViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            movieYear = itemView.findViewById(R.id.movieYear);
            movieImg = itemView.findViewById(R.id.movieImg);
        }

    }
}