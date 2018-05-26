package br.com.ilocation.emersonluiz.ilocation_app;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtils {

    public static void post(String address, String data) {
        try {
            final URL url = new URL(address);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int status = connection.getResponseCode();

            Log.d("Success", "Data send success: " + status);

        } catch (Exception e) {
            Log.e("ERROR", "Error on send information to API", e);
        }
    }
}
