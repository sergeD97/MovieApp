package com.movies.popularmovies;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.movies.popularmovies.model.Movie;
import com.movies.popularmovies.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import static com.movies.popularmovies.DetailsActivity.MOVIE_ID_EXTRA;

/**
 * Implementation of App Widget functionality.
 */
public class PosterAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    Movie movie = PreferenceUtils.getLastMovie(context);
        // Construct the RemoteViews object
        RemoteViews views = setView(context, movie);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poster_app_widget);
        Movie movie = PreferenceUtils.getLastMovie(context);
        Picasso.with(context)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(views,R.id.img,appWidgetIds);
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



    public static RemoteViews setView(Context context, Movie movie){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.poster_app_widget);

        if(movie.getTitle().equals("null")){
            Intent i = new Intent(context, LoginActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,i,0);
            views.setOnClickPendingIntent(R.id.img, pi);
            views.setTextViewText(R.id.appwidget_text,context.getString(R.string.appwidget_no));
        }else{
            Intent i = new Intent(context, DetailsActivity.class);
            i.putExtra(MOVIE_ID_EXTRA,movie);
            PendingIntent pi = PendingIntent.getActivity(context,0,i,0);
            views.setOnClickPendingIntent(R.id.img, pi);
            views.setTextViewText(R.id.appwidget_text, movie.getTitle());
        }
        return views;
    }
}

