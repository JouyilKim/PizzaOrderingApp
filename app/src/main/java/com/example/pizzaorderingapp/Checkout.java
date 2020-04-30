package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle bundle = this.getIntent().getExtras();

        items = (ArrayList)(bundle.getStringArrayList("passData")).clone();

    }
}
