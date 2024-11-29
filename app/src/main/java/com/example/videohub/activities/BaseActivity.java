package com.example.videohub.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isInternetConnected()) {
            showNoInternetDialog();
        }
    }
    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Không có kết nối Internet");
        builder.setMessage("Vui lòng kết nối Internet để tiếp tục.");
        builder.setCancelable(false);

        builder.setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isInternetConnected()) {
                    dialog.dismiss();
                } else {
                    showNoInternetDialog();
                }
            }
        });

        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                EditText editText = (EditText) getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                editText.clearFocus();
            }
        }
        return super.onTouchEvent(event);
    }
    public boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;

            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            if (capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))) {

                return isInternetAvailable();
            }
        }
        return false;
    }
    private boolean isInternetAvailable() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            HttpURLConnection urlConnection = (HttpURLConnection)
                    (new URL("https://www.google.com").openConnection());
            urlConnection.setRequestMethod("HEAD");
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);

            int responseCode = urlConnection.getResponseCode();
            urlConnection.disconnect();

            return (responseCode == 200);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
