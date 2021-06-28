package it.uniba.di.sms2021.gruppokdl.wefit.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private Location mReceivedLocations;
    double latitude, longitude;
    List<Location> listLocation;
    private OnBroadcastReceiveListener mOnBroadcastReceiveListener;

    public MyBroadcastReceiver(List<Location> listLocation, OnBroadcastReceiveListener mOnBroadcastReceiveListener) {
        this.listLocation = listLocation;
        this.mOnBroadcastReceiveListener = mOnBroadcastReceiveListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get extra data included in the Intent
        Bundle b = intent.getBundleExtra("Location");
        mReceivedLocations = (Location) b.getParcelable("Location");

        if (mReceivedLocations != null) {
            listLocation.add(mReceivedLocations);
            latitude = mReceivedLocations.getLatitude();
            longitude = mReceivedLocations.getLongitude();
        }

        mOnBroadcastReceiveListener.onBroadcastReceive();
    }

    public interface OnBroadcastReceiveListener{
        void onBroadcastReceive();
    }
}
