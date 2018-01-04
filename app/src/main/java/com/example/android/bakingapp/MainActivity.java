package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.RecipeObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<RecipeObject>>,
        customAdapter.ListItemClickListener {
    private static final int LOADER_ID = 1004;
    @BindView(R.id.recyclerView) RecyclerView mRecipeListRecyclerView;
    @BindView(R.id.loading_spinner) ProgressBar mProgressLoader;
    @BindView(R.id.noData) TextView mErrorTextView;
    public static ArrayList<RecipeObject> mRecipesList;
    public static String CLICKED_ITEM_INDEX_KEY = "itemNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LoaderCallbacks<ArrayList<RecipeObject>> callback = MainActivity.this;
        Bundle bundleForLoader = new Bundle();
        getSupportLoaderManager().initLoader(LOADER_ID, bundleForLoader, callback);
    }

    //Loader Methods
    @Override
    public Loader<ArrayList<RecipeObject>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<RecipeObject>>(this){
            ArrayList<RecipeObject> mData;
            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    forceLoad();
                }
            }
            @Override
            public ArrayList<RecipeObject> loadInBackground() {
                ArrayList<RecipeObject> recipes;
                recipes = NetworkUtils.getRecipes();
                return recipes;
            }
            public void deliverResult(ArrayList<RecipeObject> data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RecipeObject>> loader, ArrayList<RecipeObject> data) {
        mProgressLoader.setVisibility(View.GONE);
        if (data != null) {
            if (data.size() != 0) {
                GridLayoutManager layoutManager = new GridLayoutManager(this, this.getResources().getInteger(R.integer.grid_columns));
                mRecipeListRecyclerView.setLayoutManager(layoutManager);
                mRecipeListRecyclerView.setHasFixedSize(true);
                mRecipeListRecyclerView.setAdapter(new customAdapter(data, this));
                mRecipesList = data;
            }
        }else {
            mErrorTextView.setVisibility(View.VISIBLE);
            mRecipeListRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RecipeObject>> loader) {

    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
       // Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(CLICKED_ITEM_INDEX_KEY, clickedItemIndex);
        startActivity(intent);
    }
}
