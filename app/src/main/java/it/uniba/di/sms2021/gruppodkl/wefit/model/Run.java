package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.location.Location;

import java.util.List;

public class Run {

    public String date;
    public List<Location> locationList;
    public String elapsedTime;
    public float averageSpeed;
    public float burntKcal;


    public Run(){

    }

    public Run(String date, List<Location> locationList, String elapsedTime, float averageSpeed, float burntKcal){
        this.date = date;
        this.locationList = locationList;
        this.elapsedTime = elapsedTime;
        this.averageSpeed = averageSpeed;
        this.burntKcal = burntKcal;
    }


}
