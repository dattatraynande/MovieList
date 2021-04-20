package com.example.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movielist.adapter.MovieAdapter;
import com.example.movielist.model.MovieData;
import com.example.movielist.model.Search;
import com.example.movielist.retrofit.APIClient;
import com.example.movielist.retrofit.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    RecyclerView movieRecycleView;
    MovieAdapter movieAdapter;
    ArrayList<Search> movieList;
    EditText inputName;
    Button search;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieRecycleView = findViewById(R.id.movieList);
        inputName = findViewById(R.id.inputName);
        search = findViewById(R.id.search);

        loading = new ProgressDialog(MainActivity.this);
        loading.setMessage("loading");


        movieRecycleView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        movieRecycleView.setLayoutManager(layoutManager);
        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movieList);
        movieRecycleView.setAdapter(movieAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(MainActivity.this);
                getData(inputName.getText().toString());
            }
        });

        getData(inputName.getText().toString());
    }

    private void getData(String input) {
        loading.show();
        Retrofit retrofit = APIClient.getClient();
        APIInterface apiInterface = retrofit.create(APIInterface.class);
        Call<MovieData> call = apiInterface.getMovies(input, "5110ccfe");
        Log.e(TAG, "" + call.request().url());
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                loading.dismiss();
                MovieData movieData = response.body();
                if (movieData != null) {
                    if (movieData.getResponse().equalsIgnoreCase("true")) {
                        movieList = (ArrayList<Search>) movieData.getSearch();
                        movieAdapter = new MovieAdapter(MainActivity.this, movieList);
                        movieRecycleView.setAdapter(movieAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No Result Found.", Toast.LENGTH_LONG).show();
                    }

                }
                Log.e(TAG, "" + response.code());
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                loading.dismiss();
                Log.e(TAG, t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}