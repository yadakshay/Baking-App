package com.example.android.bakingapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.IngredientsObject;
import com.example.android.bakingapp.customObjects.StepObject;

import java.util.ArrayList;

import static com.example.android.bakingapp.MainActivity.mRecipesList;


public class RecipeFragment extends Fragment implements stepsListRecyclerAdapter.stepListItemClickListener{
    private int mClickedItemIndex;
    private TextView mIngredientsView;
    private ArrayList<StepObject> mStepsList;
    private ArrayList<IngredientsObject> mIngredientsList;
    // Define a new interface OnRecipeClickListener that triggers a callback in the host activity
    OnRecipeClickListener mCallback;
    RecyclerView stepsRecycle;

    @Override
    public void onStepListItemClick(int clickedItemIndex) {
        mCallback.onRecipeSelected(clickedItemIndex);
    }

    // OnRecipeClickListener interface, calls a method in the host activity named onRecipeSelected
    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    //empty public constructor
    public RecipeFragment() {
    }
    //public method to get which recipie steps to be shown
    public void setClickedItemIndex(int index){
        mClickedItemIndex = index;
    }
    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        //ListView stepsListView = (ListView) rootView.findViewById(R.id.stepsListView);
        mIngredientsList = mRecipesList.get(mClickedItemIndex).getIngredientList();
        mStepsList = mRecipesList.get(mClickedItemIndex).getStepsList();
        mIngredientsView = (TextView) rootView.findViewById(R.id.recipeIngredients);
        stepsRecycle = (RecyclerView) rootView.findViewById(R.id.stepsRecyclerView);
        for (int i=0; i<mIngredientsList.size(); i++){
            IngredientsObject iOb = mIngredientsList.get(i);
            mIngredientsView.append(iOb.getIngredient() + "\t" + iOb.getQuantity()+ " " + iOb.getMeasure() + "\n");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepsListRecyclerAdapter adapter = new stepsListRecyclerAdapter(mStepsList, this);
        stepsRecycle.setLayoutManager(layoutManager);
        stepsRecycle.setAdapter(adapter);
       // ListAdapterForStepsList adapter = new ListAdapterForStepsList(getContext(), mStepsList);
       /* stepsListView.setAdapter(adapter);
        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onRecipeSelected(position);
            }
        });*/
        return rootView;
    }

}
