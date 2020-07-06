package com.example.currentlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Double latitude = 0.0;
    Double longitude = 0.0;

    TextView locationText;

    static String TAG = "MainActivity";
    Location gps_loc = null, network_loc = null, final_loc = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = findViewById(R.id.locationText);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "Not Granted!", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show();
        }

        try{
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (gps_loc != null) {
            final_loc = gps_loc;
            Log.d("finalLoc", String.valueOf(final_loc));
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();

            Log.d("lanGPS_LOC", String.valueOf(latitude));
            Log.d("longtGPS_LOC", String.valueOf(longitude));
        } else if (network_loc != null) {
            final_loc = network_loc;
            Log.d("finalLoc", String.valueOf(final_loc));
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();

            Log.d("lannet_LOC", String.valueOf(latitude));
            Log.d("longtnet_LOC", String.valueOf(longitude));
        } else {
            latitude = 0.0;
            longitude = 0.0;

            Log.d("lanELSE_LOC", String.valueOf(latitude));
            Log.d("longtELSE_LOC", String.valueOf(longitude));
        }


        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);

            if (addresses != null && addresses.size()>0){
                String country = addresses.get(0).getCountryName();

                locationText.setText(country);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
