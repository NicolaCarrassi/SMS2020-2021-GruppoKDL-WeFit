package it.uniba.di.sms2021.gruppodkl.wefit.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class MyLocation implements Parcelable {

    private float latitude;
    private float longitude;

    public MyLocation(){

    }

    public MyLocation(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }


    protected MyLocation(Parcel in) {
        latitude = in.readFloat();
        longitude = in.readFloat();
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


    // implementazione parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    public static final Creator<MyLocation> CREATOR = new Creator<MyLocation>() {
        @Override
        public MyLocation createFromParcel(Parcel in) {
            return new MyLocation(in);
        }

        @Override
        public MyLocation[] newArray(int size) {
            return new MyLocation[size];
        }
    };

}
