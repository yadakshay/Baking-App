package com.example.android.bakingapp;

import android.support.annotation.Nullable;

import com.example.android.bakingapp.customObjects.IngredientsObject;
import com.example.android.bakingapp.customObjects.RecipeObject;
import com.example.android.bakingapp.customObjects.StepObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Akshay on 17-09-2017.
 */

public class NetworkUtils {
    private static String URL_FOR_RECIPE = "https://go.udacity.com/android-baking-app-json";

    public static ArrayList<RecipeObject> getRecipes(){
        //create URL
        URL url = null;
        String response = null;
        try {
            url = new URL(URL_FOR_RECIPE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //get Response from URL
        try {
            response = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // extract data from json
        if(response != null) {
            ArrayList<RecipeObject> recipes = extractFromJSON(response);
            return recipes;
        }else {return null; }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    @Nullable
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static ArrayList<RecipeObject> extractFromJSON(String data){
        ArrayList<RecipeObject> allRecipes = new ArrayList<>();
        try {
            JSONArray recipeArray = new JSONArray(data);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipe = recipeArray.getJSONObject(i);
                String name = recipe.getString("name");
                JSONArray ingredientsArray = recipe.getJSONArray("ingredients");
                JSONArray stepsArray = recipe.getJSONArray("steps");

                ArrayList<IngredientsObject> ingredientsList = new ArrayList<>();
                ArrayList<StepObject> stepsList = new ArrayList<>();
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ing = ingredientsArray.getJSONObject(j);
                    String quantity = ing.getString("quantity");
                    String measure = ing.getString( "measure");
                    String ingredient = ing.getString("ingredient");
                    ingredientsList.add(new IngredientsObject(quantity, measure, ingredient));
                }
                for (int k = 0; k < stepsArray.length(); k++){
                    JSONObject step = stepsArray.getJSONObject(k);
                    String shortDesc = step.getString("shortDescription");
                    String desc = step.getString("description");
                    String vdoUrl = step.getString("videoURL");
                    String thumbNail = step.getString("thumbnailURL");
                    stepsList.add(new StepObject(shortDesc, desc, vdoUrl, thumbNail));
                }
                allRecipes.add(new RecipeObject(name, ingredientsList, stepsList));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allRecipes;
    }
}
