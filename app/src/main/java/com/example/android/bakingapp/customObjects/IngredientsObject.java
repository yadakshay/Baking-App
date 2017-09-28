package com.example.android.bakingapp.customObjects;

/**
 * Created by Akshay on 17-09-2017.
 *
 * This is a object which represents each ingredient
 */

public class IngredientsObject {
    private String mQuantity, mMeasure, mIngredient;
    //constructor
    public IngredientsObject(String quant, String measure, String ingredient){
        mQuantity = quant;
        mMeasure = measure;
        mIngredient = ingredient;
    }
    //return meathods
    public String getQuantity(){return mQuantity;}
    public String getMeasure(){return mMeasure;}
    public String getIngredient(){return mIngredient;}
}
