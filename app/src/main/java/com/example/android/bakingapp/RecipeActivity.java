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
    RecipeFragment recipeFragment = null;
    StepFragment sf = null;
    private String SAVED_INSTANCE_FRAGMENT_KEY = "myFragmentName";
    private String SAVED_INSTANCE_FRAGMENT_KEY1 = "myFragmentName1";
    private String CURRENT_FRAGMENT_KEY = "currentFragmentKey";
    private String STEP_FRAGMENT = "stepFragment";
    private String RECIPE_FRAGMENT = "recipeFragment";
    private String currentFragment = RECIPE_FRAGMENT; // recipeFragment or stepFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if(savedInstanceState != null) {
            if(savedInstanceState.getString(CURRENT_FRAGMENT_KEY).matches(RECIPE_FRAGMENT)) {
                recipeFragment = (RecipeFragment) getSupportFragmentManager().getFragment(savedInstanceState, SAVED_INSTANCE_FRAGMENT_KEY);
                currentFragment = RECIPE_FRAGMENT;
            }
            if(savedInstanceState.getString(CURRENT_FRAGMENT_KEY).matches(STEP_FRAGMENT)){
                sf= (StepFragment) getSupportFragmentManager().getFragment(savedInstanceState, SAVED_INSTANCE_FRAGMENT_KEY1);
                currentFragment = STEP_FRAGMENT;
            }
        }

        else {
            //new RecipeFragment
            Intent intent = getIntent();
            recipeNo = intent.getIntExtra(CLICKED_ITEM_INDEX_KEY, 0);
            recipeFragment = new RecipeFragment();
            recipeFragment.setClickedItemIndex(recipeNo);

        }

        //    isLargeScreen = false;
            //get clicked recipe from the intent

            // get instance of FragmentManager
            fragmentManager = getSupportFragmentManager();
            //setClickedItemIndex for the fragment

            // fragmentManager.popBackStackImmediate();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            if(currentFragment == RECIPE_FRAGMENT) {
                trans.replace(R.id.recipe_fragment_holder, recipeFragment);
            }
            if(currentFragment == STEP_FRAGMENT) {
                trans.replace(R.id.recipe_fragment_holder, sf);
            }
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if (findViewById(R.id.steps_fragment_holder) != null) {
                isLargeScreen = true;
                stepFragmentHolderToUse = R.id.steps_fragment_holder;
                defaultStepObject = mRecipesList.get(recipeNo).getStepsList().get(0);
                if(savedInstanceState != null) {
                    sf= (StepFragment) getSupportFragmentManager().getFragment(savedInstanceState, SAVED_INSTANCE_FRAGMENT_KEY1);
                }else{
                    sf = new StepFragment();
                    sf.setStepObject(defaultStepObject);
                }
                trans.replace(stepFragmentHolderToUse, sf);
            }
            trans.commit();


    }

    @Override
    public void onRecipeSelected(int position) {

        sf = new StepFragment();
        currentPosition = position;
        mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(position);
        sf.setStepObject(mClickedStepObject);
        //fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                    .replace(stepFragmentHolderToUse, sf)
                    .commit();
        sf.setRetainInstance(true);
        currentFragment = STEP_FRAGMENT;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeFragment != null) {
            // getSupportFragmentManager().
            if (currentFragment == RECIPE_FRAGMENT || isLargeScreen) {
                getSupportFragmentManager().putFragment(outState, SAVED_INSTANCE_FRAGMENT_KEY, recipeFragment);
            }
        }
        if (currentFragment == STEP_FRAGMENT || isLargeScreen){
            getSupportFragmentManager().putFragment(outState, SAVED_INSTANCE_FRAGMENT_KEY1, sf);
        }
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragment);
    }

    @Override
    public void onNextSelected(String nextOrPrev) {
        if (nextOrPrev.matches(NEXT_KEY)) {
            sf = new StepFragment();
            if (currentPosition < (mRecipesList.get(recipeNo).getStepsList().size() - 1)) {
                currentPosition = currentPosition + 1;
                mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(currentPosition);
                sf.setStepObject(mClickedStepObject);
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                   //     .addToBackStack(null)
                        .replace(stepFragmentHolderToUse, sf)
                        .commit();
                sf.setRetainInstance(true);
            }else{
                Toast.makeText(this, "Last Step", Toast.LENGTH_SHORT).show();
            }
        }if (nextOrPrev.matches(PREVIOUS_KEY)){
            sf = new StepFragment();
            if (currentPosition > 0) {
                currentPosition = currentPosition - 1;
                mClickedStepObject = mRecipesList.get(recipeNo).getStepsList().get(currentPosition);
                sf.setStepObject(mClickedStepObject);
                fragmentManager.popBackStackImmediate();
                fragmentManager.beginTransaction()
                //        .addToBackStack(null)
                        .replace(stepFragmentHolderToUse, sf)
                        .commit();
                sf.setRetainInstance(true);
            }else {
                Toast.makeText(this, "First Step", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
