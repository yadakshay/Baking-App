package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.customObjects.StepObject;

import static com.example.android.bakingapp.MainActivity.CLICKED_ITEM_INDEX_KEY;
import static com.example.android.bakingapp.MainActivity.mRecipesList;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener, StepFragment.OnNextClickListener {
    private FragmentManager fragmentManager;
    private int recipeNo;
    private StepObject mClickedStepObject;
    private int currentPosition = 0;
    public static final String NEXT_KEY = "next";
    public static final String PREVIOUS_KEY = "prev";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //get clicked recipe from the intent
        Intent intent = getIntent();
        recipeNo = intent.getIntExtra(CLICKED_ITEM_INDEX_KEY, 0);
        // get instance of FragmentManager
        fragmentManager = getSupportFragmentManager();
        //new RecipeFragment
        RecipeFragment recipeFragment = new RecipeFragment();
        //setClickedItemIndex for the fragment
        recipeFragment.setClickedItemIndex(recipeNo);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_fragment_holder, recipeFragment)
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onRecipeSelected(int position) {
        StepFragment sf = new StepFragment();
        currentPosition = position;
        mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(position);
        sf.setStepObject(mClickedStepObject);
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.recipe_fragment_holder, sf)
                .commit();
    }

    @Override
    public void onNextSelected(String nextOrPrev) {
        if (nextOrPrev.matches(NEXT_KEY)) {
            StepFragment sf = new StepFragment();
            if (currentPosition < (mRecipesList.get(recipeNo).getStepsList().size() - 1)) {
                currentPosition = currentPosition + 1;
                mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(currentPosition);
                sf.setStepObject(mClickedStepObject);
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.recipe_fragment_holder, sf)
                        .commit();
            }else{
                Toast.makeText(this, "Last Step", Toast.LENGTH_SHORT).show();
            }
        }if (nextOrPrev.matches(PREVIOUS_KEY)){
            StepFragment sf = new StepFragment();
            if (currentPosition > 0) {
                currentPosition = currentPosition - 1;
                mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(currentPosition);
                sf.setStepObject(mClickedStepObject);
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.recipe_fragment_holder, sf)
                        .commit();
            }else {
                Toast.makeText(this, "First Step", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
