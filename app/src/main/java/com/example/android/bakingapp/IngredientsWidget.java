package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

      //  CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object

      //  views.setTextViewText(R.id.appwidget_text, widgetText);
      //  new LoadRecipesForWidget(views, appWidgetManager, appWidgetId).execute();
        // Instruct the widget manager to update the widget
      //  appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            Intent serviceIntent = new Intent(context, StackWidgetServce.class);
            views.setRemoteAdapter(appWidgetIds[i], R.id.stackWidgetView, serviceIntent);
            views.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

