package com.vincent.applicationmeteo.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class LocalisationFinder implements LocationListener {

    protected String latitude;
    protected String longitude;
    protected Context context;
    protected boolean active;
    protected LocalisationFinderListener listener;

    public interface LocalisationFinderListener {
        void onLocationFound(String latitude, String longitude);
    }

    public LocalisationFinder(Context activityContext, LocalisationFinderListener listener) {
        this.latitude = "0";
        this.longitude = "0";
        this.context = activityContext;
        this.listener = listener;

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        listener.onLocationFound(latitude, longitude);
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        Log.d("location_test",""+locations.size());
        LocationListener.super.onLocationChanged(locations);
        Toast.makeText(context, "Location changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
        Toast.makeText(context, "Provider enabled", Toast.LENGTH_SHORT).show();
        active = true;
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
        Toast.makeText(context, "Provider disabled", Toast.LENGTH_SHORT).show();
        active = false;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
