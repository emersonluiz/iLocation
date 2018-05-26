package br.com.ilocation.emersonluiz.ilocation_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    Activity activity = MainActivity.this;
    String wantPermission = Manifest.permission.READ_PHONE_STATE;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView phone = findViewById(R.id.phone);
        phone.setText(getImei());

        fused();
    }

    @SuppressLint("MissingPermission")
    private String getImei() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return phoneMgr.getImei();
        } else {
            return phoneMgr.getDeviceId();
        }
    }

    protected LocationRequest createLocationRequest() {
        @SuppressLint("RestrictedApi") LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return mLocationRequest;
    }

    @SuppressWarnings("MissingPermission")
    private void fused() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent mRequestLocationUpdatesIntent = new Intent(this, LocationService.class);

        PendingIntent mRequestLocationUpdatesPendingIntent = PendingIntent.getService(getApplicationContext(), 0,
                mRequestLocationUpdatesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mFusedLocationClient.requestLocationUpdates(createLocationRequest(), mRequestLocationUpdatesPendingIntent);
    }
}
