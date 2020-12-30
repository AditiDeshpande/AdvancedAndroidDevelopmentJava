package com.example.appwidgetsample;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import java.util.Date;
import java.text.DateFormat;

import java.text.DateFormat;

/**
 * Implementation of App Widget functionality.
 *
 * This file is the widget provider , the Java file that defines
 * the behavior for your widget. The key task for a widget provider
 * is to handle widget update intents. App widgets extend the
 * AppWidgetProvider , which in turn extends BroadcastReceiver.
 */

/*
Unlike activities , where u only inflate the layout once and then
modify it in place as new data appears , the entire app widget layout
must be reconstructed and redisplayed each time the widget receives
an update intent.

 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String mSharedPrefFile  =
            "com.example.android.appwidgetsample";
    private static final String COUNT_KEY = "count";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        SharedPreferences prefs = context.getSharedPreferences(
                mSharedPrefFile , 0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId , 0);
        count++;

        String dateString =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_id, String.valueOf(appWidgetId));

        views.setTextViewText(R.id.appwidget_update,
                context.getResources().getString(
                        R.string.date_count_format , count ,
                        dateString
                ));

        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putInt(COUNT_KEY + appWidgetId , count);
        prefEditor.apply();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /*
    This method is called the first time the widget runs and again
    each time the widget receives an update request(a broadcast intent)
     */
    /*
    The data your app widget contains can be updated by 2 ways
    1. The widget can update itself at regular intervals.
    u can define the interval in the widget's provider file
    2. The widget's associated app can request a widget update
    explicitly
    */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}