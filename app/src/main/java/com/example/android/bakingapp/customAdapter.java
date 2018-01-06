package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.customObjects.RecipeObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Akshay on 22-09-2017.
 */

public class customAdapter extends RecyclerView.Adapter<customAdapter.customViewHolder> {
    private ArrayList<RecipeObject> mRecipes;
    final private ListItemClickListener mOnClickListener;
    Context mContext;
    //interface for click listener
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    public customAdapter(ArrayList<RecipeObject> recipe, ListItemClickListener clickListener){
        mRecipes = recipe;
        mOnClickListener = clickListener;
    }

    @Override
    public customAdapter.customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        customViewHolder viewHolder = new customViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(customAdapter.customViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class customViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeName;
        ImageView imageView;
        public customViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipeName);
            imageView = (ImageView) itemView.findViewById(R.id.recipeImageHolder);
            itemView.setOnClickListener(this);
        }
        void bind(int listIndex) {
            recipeName.setText(mRecipes.get(listIndex).getRecipeName());
            if(mRecipes.get(listIndex).getRecipeImage() != null){
                if (!mRecipes.get(listIndex).getRecipeImage().matches("")){
                    Picasso.with(mContext).load(mRecipes.get(listIndex).getRecipeImage()).into(imageView);
                }
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
