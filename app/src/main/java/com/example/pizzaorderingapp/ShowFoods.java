package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class ShowFoods extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_foods);
    }


    private boolean isOnline() {
        ConnectivityManager con = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =
                con.getActiveNetworkInfo();
        if (networkInfo != null &&
                networkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
}
