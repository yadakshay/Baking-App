package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.customObjects.IngredientsObject;
import com.example.android.bakingapp.customObjects.RecipeObject;

import java.util.ArrayList;

public class StackWidgetServce extends RemoteViewsService {
    private ArrayList<RecipeObject> mRecipes;

    public StackWidgetServce() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    private class StackRemoteViewsFactory implements RemoteViewsFactory {
        private Context mContext;
        public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mRecipes = NetworkUtils.getRecipes();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mRecipes.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_stack_item);
            if (position <= getCount()) {
                RecipeObject ro = mRecipes.get(position);
                if(ro != null){
                    rv.setTextViewText(R.id.header,ro.getRecipeName());
                    ArrayList<IngredientsObject> mIngredientsList = ro.getIngredientList();
                    String s = "";
                    for (int i=0; i<mIngredientsList.size(); i++){
                        IngredientsObject iOb = mIngredientsList.get(i);
                        s = s + iOb.getIngredient() + "\t" + iOb.getQuantity()+ " " + iOb.getMeasure() + "\n";
                    }
                    rv.setTextViewText(R.id.ingredients, s);
                }
            }
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
