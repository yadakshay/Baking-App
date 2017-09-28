package com.example.android.bakingapp.customObjects;

import java.util.ArrayList;

/**
 * Created by Akshay on 17-09-2017.
 */

public class RecipeObject {
    private String mRecipeName;
    private ArrayList<IngredientsObject> mIngredientList;
    private ArrayList<StepObject> mStepsList;
    //constructor
    public RecipeObject(String name, ArrayList<IngredientsObject> ingredientsList, ArrayList<StepObject> stepsList){
        mRecipeName = name;
        mIngredientList = ingredientsList;
        mStepsList = stepsList;
    }
    //return methods
    public String getRecipeName(){return mRecipeName;}
    public ArrayList<IngredientsObject> getIngredientList(){return mIngredientList;}
    public ArrayList<StepObject> getStepsList(){return mStepsList;}
}
