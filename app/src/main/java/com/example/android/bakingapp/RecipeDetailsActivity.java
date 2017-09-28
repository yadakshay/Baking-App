package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.IngredientsObject;
import com.example.android.bakingapp.customObjects.StepObject;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.CLICKED_ITEM_INDEX_KEY;
import static com.example.android.bakingapp.MainActivity.mRecipesList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private TextView mIngredientsView;
    private ArrayList<IngredientsObject> mIngredientsList;
    private ArrayList<StepObject> mStepsList;
    public static String INETENT_EXTRA_KEY = "stepNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        int clickedItemIndex = intent.getIntExtra(CLICKED_ITEM_INDEX_KEY, 0);
        mIngredientsList = mRecipesList.get(clickedItemIndex).getIngredientList();
        mStepsList = mRecipesList.get(clickedItemIndex).getStepsList();

        mIngredientsView = (TextView) findViewById(R.id.recipeIngredients);

        for (int i=0; i<mIngredientsList.size(); i++){
            IngredientsObject iOb = mIngredientsList.get(i);
            mIngredientsView.append(iOb.getIngredient() + "\t" + iOb.getQuantity()+ " " + iOb.getMeasure() + "\n");
        }
        ListView stepsListView = (ListView) findViewById(R.id.stepsListView);
        ListAdapterForStepsList adapter = new ListAdapterForStepsList(this, mStepsList);
        stepsListView.setAdapter(adapter);

        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StepObject clickedStep = mStepsList.get(position);

                Intent intent = new Intent(RecipeDetailsActivity.this, StepsDetailsActivity.class);
                intent.putExtra(INETENT_EXTRA_KEY, mStepsList.get(position).getVideoURL());
                startActivity(intent);
            }
        });
    }
}
