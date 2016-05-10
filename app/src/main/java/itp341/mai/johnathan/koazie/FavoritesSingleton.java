package itp341.mai.johnathan.koazie;


import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FavoritesSingleton {

    // Instance Variables
    private ArrayList<Result> mFavorites;

    private Context mAppContext;
    private static FavoritesSingleton sFavoritesList;

    private FavoritesSingleton(Context c) {
        mAppContext = c;
        mFavorites = new ArrayList<Result>();
        mFavorites.add(new Result("Facility Name", "", "", "0000000000", "", ""));
    }

    // Singleton Getter
    public static FavoritesSingleton get (Context c) {
        if (sFavoritesList == null) {
            sFavoritesList = new FavoritesSingleton(c.getApplicationContext());
        }
        return sFavoritesList;
    }

    // Get all notes
    public ArrayList<Result> getFavorites() {
        return mFavorites;
    }

    // Get single note given index
    public Result getFavorite(int index) {
        return mFavorites.get(index);
    }

    // Add a note to the list
    public void addFavorite(Result result) {
        mFavorites.add(result);
    }

    // Remove a note from the list
    public void removeFavorite(int index) {
        mFavorites.remove(index);
    }

}
