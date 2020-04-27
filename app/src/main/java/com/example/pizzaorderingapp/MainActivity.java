package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent goOrder = new Intent(MainActivity.this, OrderMenu.class);
                startActivity(goOrder);
            }
        });

        //click listener to go user setting activity
        btnGoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSettings = new Intent(MainActivity.this, OrderMenu.class);
                startActivity(goSettings);
            }
        });

    }
}
