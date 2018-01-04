package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.customObjects.StepObject;

import static com.example.android.bakingapp.MainActivity.CLICKED_ITEM_INDEX_KEY;
import static com.example.android.bakingapp.MainActivity.mRecipesList;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnRecipeClickListener, StepFragment.OnNextClickListener {
    private FragmentManager fragmentManager;
    private int recipeNo;
    private StepObject mClickedStepObject, defaultStepObject;
    private int currentPosition = 0;
    public static final String NEXT_KEY = "next";
    public static final String PREVIOUS_KEY = "prev";
    private static boolean isLargeScreen = false;
    private int stepFragmentHolderToUse = R.id.recipe_fragment_holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        isLargeScreen = false;
        //get clicked recipe from the intent
        Intent intent = getIntent();
        recipeNo = intent.getIntExtra(CLICKED_ITEM_INDEX_KEY, 0);
        // get instance of FragmentManager
        fragmentManager = getSupportFragmentManager();
        //new RecipeFragment
        RecipeFragment recipeFragment = new RecipeFragment();

        //setClickedItemIndex for the fragment
        recipeFragment.setClickedItemIndex(recipeNo);
        fragmentManager.popBackStackImmediate();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.addToBackStack(null)
             .add(R.id.recipe_fragment_holder, recipeFragment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(findViewById(R.id.steps_fragment_holder) != null){
            isLargeScreen = true;
            stepFragmentHolderToUse = R.id.steps_fragment_holder;
            defaultStepObject = mRecipesList.get(recipeNo).getStepsList().get(0);
            StepFragment step = new StepFragment();
            step.setStepObject(defaultStepObject);
            trans.addToBackStack(null)
                 .add(stepFragmentHolderToUse, step);
        }
        trans.commit();
    }

    @Override
    public void onRecipeSelected(int position) {

        StepFragment sf = new StepFragment();
        currentPosition = position;
        mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(position);
        sf.setStepObject(mClickedStepObject);
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(stepFragmentHolderToUse, sf)
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
                        .replace(stepFragmentHolderToUse, sf)
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
                        .replace(stepFragmentHolderToUse, sf)
                        .commit();
            }else {
                Toast.makeText(this, "First Step", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
