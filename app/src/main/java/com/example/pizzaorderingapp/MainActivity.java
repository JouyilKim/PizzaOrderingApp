package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGoOrder;
    Button btnGoSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoOrder = findViewById(R.id.btnGoOrder);
        btnGoSettings = findViewById(R.id.btnGoSettings);

        //click listener to go ordering activity
        btnGoOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent goOrder = new Intent(MainActivity.this, OrderMenu.class);
                    startActivity(goOrder);
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "You are not Connected to the Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //click listener to go user setting activity
        btnGoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent goSettings = new Intent(MainActivity.this, OrderMenu.class);
                    startActivity(goSettings);
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "You are not Connected to the Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo==null) &&networkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }
}
