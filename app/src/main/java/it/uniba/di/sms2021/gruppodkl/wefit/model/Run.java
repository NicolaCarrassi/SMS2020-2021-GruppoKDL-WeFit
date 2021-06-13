package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class Run {

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

        return String.valueOf(caloriesBurnt);
    }


}
