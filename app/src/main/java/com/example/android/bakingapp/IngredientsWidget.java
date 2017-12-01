package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.example.android.bakingapp.customObjects.RecipeObject;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

      //  CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
      //  views.setTextViewText(R.id.appwidget_text, widgetText);
        new LoadRecipesForWidget(views, appWidgetManager, appWidgetId).execute();
        // Instruct the widget manager to update the widget
      //  appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public class LoadRecipesForWidget extends AsyncTask<Void, Void, ArrayList<RecipeObject>>{
        private RemoteViews rv;
        private AppWidgetManager wManager;
        private int widgetId;
        public LoadRecipesForWidget(RemoteViews views, AppWidgetManager awm, int wId){
            rv = views;
            wManager = awm;
            widgetId = wId;
        }
        @Override
        protected ArrayList<RecipeObject> doInBackground(Void... params) {
            return NetworkUtils.getRecipes();
        }

        @Override
        protected void onPostExecute(ArrayList<RecipeObject> recipeObjects) {
           // super.onPostExecute(recipeObjects);
            String s = recipeObjects.get(0).getIngredientList().get(0).getIngredient();
            rv.setTextViewText(R.id.appwidget_text, s);
            wManager.updateAppWidget(widgetId, rv);
        }
    }
}

