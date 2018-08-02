package com.example.andresteve07.locationoldphones;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 666);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        Log.d("LOCATION_STEVE", "HELLO!!");
        getLocationSDK();

        /*
        LocationService locationService = new LocationService(this.getApplicationContext());
        //Log.d("ACTIVITY", );
        locationService.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .subscribe(location -> {
            Log.d("LOCATION_STEVE", location.toString());
        },throwable -> {
            Log.e("LOCATION_STEVE", "Error", throwable);
        },() -> {
            Log.d("LOCATION_STEVE", "ON COMPLETED");
        });
        */

        LocationService locationService = new LocationService(this.getApplicationContext());
        locationService.getAddressesFromLocation()
                .subscribe(address -> {
                    Log.d("ADDRESS_STEVE", address.toString());
                        }, throwable -> {
                    Log.e("ADDRESS_STEVE", "ERROR", throwable);
                        },() -> {
                    Log.d("ADDRESS_STEVE", "COMPLETED");
                        });

        //-31.452259, -64.194883
        //-31.443976, -64.200102
        //-31.431874, -64.191515
        //-31.429106, -64.194970
        //-31.425796, -64.195356
        Location location = new Location("STEVE");
        location.setLatitude(-31.425796);
        location.setLongitude(-64.195356);
        locationService.getAddressesFromLocation(location)
                .subscribe(address -> {
                    Log.d("LOCATION_HARDCODED", address.toString());
                }, throwable -> {
                    Log.e("LOCATION_HARDCODED", "ERROR", throwable);
                },() -> {
                    Log.d("LOCATION_HARDCODED", "COMPLETED");
                });
        //thoroughfare -> calle String
        //feature -> numero String
        //locality ->

    }
    public void getLocationSDK(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        Log.d("LOCATION_STEVE", "GOTTEN: " + location.toString());
                    }
                    Log.d("LOCATION_STEVE", "GOTTEN: NULL");
                });
    }
}
