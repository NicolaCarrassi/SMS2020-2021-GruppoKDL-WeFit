package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.location.Location;

import java.util.List;

public class Run {

    public String date;
    public List<Location> locationList;
    public String elapsedTime;
    public float averageSpeed;
    public float burntKcal;
    public float distance;



    public Run(){

    }

    public Run(String date, List<Location> locationList, String elapsedTime, float averageSpeed, float burntKcal, float distance){
        this.date = date;
        this.locationList = locationList;
        this.elapsedTime = elapsedTime;
        this.averageSpeed = averageSpeed;
        this.burntKcal = burntKcal;
        this.distance = distance;
    }


    public String convertRunDistance(){
        String res = "";
        int distanceInMeters = (int) distance;

        if(distanceInMeters % 1000 != distanceInMeters)
            res += distanceInMeters/1000  + " km ";

        return res + distanceInMeters%1000 + " m";
    }


}
