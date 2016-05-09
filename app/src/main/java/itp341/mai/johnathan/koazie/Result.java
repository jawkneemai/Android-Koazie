package itp341.mai.johnathan.koazie;

import java.util.Date;

public class Result {

    private String mTitle;
    private double mLatitude;
    private double mLongitude;

    public Result() {
        mTitle = "";
        mLatitude = 0.0;
        mLongitude = 0.0;
    }

    public Result(String title, double latitude, double longitude) {
        mTitle = title;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    // Getters (Shouldn't need to reset any values after creation)

    public String getTitle() {
        return mTitle;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
