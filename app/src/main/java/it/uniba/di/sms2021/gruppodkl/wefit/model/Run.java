package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Run implements Parcelable {

    public interface RunKeys{
        String DATE = "date";
    }

    public String date;
    public List<MyLocation> locationList;
    public String elapsedTime;
    public float averageSpeed;
    public float burntKcal;
    public float distance;


    public Run(){
        //empty constructor
    }

    public Run(String date, List<Location> locationList, String elapsedTime, float averageSpeed, float burntKcal, float distance){
        this.date = date;
        this.locationList = new ArrayList<>();
        this.elapsedTime = elapsedTime;
        this.averageSpeed = averageSpeed;
        this.burntKcal = burntKcal;
        this.distance = distance;

        for(Location location: locationList)
            this.locationList.add(MyLocation.createFromAndroidLocation(location));

    }


    public String convertRunDistance(){
        String res = "";
        int distanceInMeters = (int) distance;

        if(distanceInMeters % 1000 != distanceInMeters)
            res += distanceInMeters/1000  + " km ";

        return res + distanceInMeters%1000 + " m";
    }

    public String convertKcal(){
        int caloriesBurnt = (int) burntKcal;

        return caloriesBurnt + " kcal";
    }

    @SuppressLint("DefaultLocale")
    public String convertAvgSpeed(){
        return String.format("%.2f", averageSpeed) + "m/s";
    }


    //implementazione parcelable

    protected Run(Parcel in) {
        date = in.readString();
        locationList = in.createTypedArrayList(MyLocation.CREATOR);
        elapsedTime = in.readString();
        averageSpeed = in.readFloat();
        burntKcal = in.readFloat();
        distance = in.readFloat();
    }

    public static final Creator<Run> CREATOR = new Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel in) {
            return new Run(in);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeTypedList(locationList);
        dest.writeString(elapsedTime);
        dest.writeFloat(averageSpeed);
        dest.writeFloat(burntKcal);
        dest.writeFloat(distance);
    }



}
