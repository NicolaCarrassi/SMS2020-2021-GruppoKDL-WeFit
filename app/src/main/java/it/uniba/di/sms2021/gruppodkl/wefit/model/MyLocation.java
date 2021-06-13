package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.location.Location;

public class MyLocation {

    private float latitude;
    private float longitude;

    public MyLocation(){

    }

    public MyLocation(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public static MyLocation createFromAndroidLocation(Location location){
        MyLocation myLocation = new MyLocation();

        myLocation.latitude = (float) location.getLatitude();
        myLocation.longitude = (float) location.getLongitude();

        return myLocation;
    }


    public float getLatitude(){
        return latitude;
    }

    public float getLongitude(){
        return longitude;
    }

    public void setLatitude(float latitude){
        this.latitude = latitude;
    }

    public void setLongitude(float longitude){
        this.longitude = longitude;
    }
}
