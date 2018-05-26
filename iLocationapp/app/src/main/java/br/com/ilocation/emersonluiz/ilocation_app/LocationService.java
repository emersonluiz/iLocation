package br.com.ilocation.emersonluiz.ilocation_app;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Scanner;

public class LocationService extends IntentService {

    private final String CU_INFOS = "CU_INFOS";
    private final String CU_OFFLINE = "CU_OFFLINE";
    private final String CU_API_URL = "http://www.mocky.io/v2/5afe31c73200008a00f1ae89";

    public LocationService() {
        super("LocationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (LocationResult.hasResult(intent)) {
            LocationResult locationResult = LocationResult.extractResult(intent);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                Log.d("location", "accuracy: " + location.getAccuracy() + " lat: " + location.getLatitude() + " lon: " + location.getLongitude());

                DecimalFormat df = new DecimalFormat("#.###");
                String lat = df.format(location.getLatitude());
                String lon = df.format(location.getLongitude());

                try {

                    FileInputStream fis = openFileInput(CU_INFOS);
                    Scanner scanner = new Scanner(fis);
                    scanner.useDelimiter(";");
                    String latitude = scanner.next();
                    String longitude = scanner.next();
                    scanner.close();

                    if(latitude.equalsIgnoreCase(lat) && longitude.equalsIgnoreCase(lon)) {
                        if(isOnline()) {
                            readFile(CU_OFFLINE);
                        }
                    } else {
                        fileWriter(lat, lon, CU_INFOS);

                        if(isOnline()) {
                            HTTPUtils.post(CU_API_URL, getJson(location.getLatitude(), location.getLongitude()));
                            readFile(CU_OFFLINE);
                        } else {
                            fileWriterOffline(lat, lon, CU_OFFLINE);
                        }
                    }

                } catch (Exception e) {
                    fileWriter(lat, lon, CU_INFOS);

                    if(isOnline()) {
                        HTTPUtils.post(CU_API_URL, getJson(location.getLatitude(), location.getLongitude()));
                        readFile(CU_OFFLINE);
                    } else {
                        fileWriterOffline(lat, lon, CU_OFFLINE);
                    }

                }

            }
        }
    }

    private String getJson(Double lat, Double lng) {
        String data = "{'device':{'imei':'"+getImei()+"'}, 'latitude':'" + lat + "', 'longitude':'" + lng + "'}";
        return data;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    private String getImei() {

        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return phoneMgr.getImei();
        } else {
            return phoneMgr.getDeviceId();
        }
    }

    private void fileWriter(String lat, String lon, String name) {
        try {
            FileOutputStream fos;
            lon = ";" + lon;
            fos = openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(lat.getBytes());
            fos.write(lon.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("Error", "Error on file writer cache");
        }
    }

    private void fileWriterOffline(String lat, String lon, String name) {
        try {
            FileOutputStream fos;
            lon = "|" + lon;
            fos = openFileOutput(name, Context.MODE_APPEND);
            fos.write(";".getBytes());
            fos.write(lat.getBytes());
            fos.write(lon.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("Error", "Error on file writer offline");
        }
    }

    private void removeFile(String name) {
        try {
            deleteFile(name);
        } catch (Exception e) {
            Log.e("Error", "Error on remove file");
        }
    }

    private void readFile(String name) {
        try {
            FileInputStream fis = openFileInput(name);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter(";");
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                String v = scanner.next();

                String[] geo = v.split("|");

                if (sb.length() > 0) {
                    sb.append(",");
                }

                sb.append("{'latitude':'" + geo[0] + "', 'longitude':'" + geo[1] + "'}");

            }
            scanner.close();

            if (sb.length() > 0) {
                HTTPUtils.post(CU_API_URL, "[" + sb.toString() + "]");
                removeFile(name);
            }
        } catch (Exception e) {
            Log.e("Error", "Error on file read file offline");
        }
    }
}
