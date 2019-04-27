package com.movies.popularmovies.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.movies.popularmovies.PosterAppWidget;
import com.movies.popularmovies.model.Movie;

/**
 * Created by Serge Pessokho on 26/04/2019.
 */

public class PreferenceUtils {

    public static final String MOVIE_ID_KEY = "movieid";
    public static final String MOVIE_TITLE_KEY = "movietitle";
    public static final String MOVIE_POSTER_KEY = "movieposter";
    public static final String MOVIE_SYNOP_KEY = "moviesynop";
    public static final String MOVIE_RATE_KEY = "movierate";
    public static final String MOVIE_DATE_KEY = "moviedate";

    public static void setLastMovie(Context context, Movie movie){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            //edit.putInt(MOVIE_ID_KEY, movie.getId());
            edit.putString(MOVIE_TITLE_KEY, movie.getTitle());
            edit.putString(MOVIE_POSTER_KEY, movie.getPosterUrl());
            edit.putString(MOVIE_SYNOP_KEY, movie.getSynopsis());
            edit.putString(MOVIE_RATE_KEY, String.valueOf(movie.getRate()));
            edit.putString(MOVIE_DATE_KEY, movie.getDate());


        //////////////////////////////
        ComponentName appWidget = new ComponentName(context, PosterAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = PosterAppWidget.setView(context,movie);
        appWidgetManager.updateAppWidget(appWidget, views);
    }

    public static Movie getLastMovie(Context context){
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
        Movie movie  = new Movie();
        //movie.setId(s.getInt(MOVIE_ID_KEY, -1));
        movie.setTitle(s.getString(MOVIE_TITLE_KEY, "null"));
        movie.setPosterUrl(s.getString(MOVIE_POSTER_KEY, "io.jpg"));
        movie.setSynopsis(s.getString(MOVIE_SYNOP_KEY, "Avenger Endgame"));
        movie.setRate(Double.parseDouble(s.getString(MOVIE_RATE_KEY, "5.0")));
        movie.setDate(s.getString(MOVIE_DATE_KEY, "02/03/2016"));
        return movie;
    }
}
