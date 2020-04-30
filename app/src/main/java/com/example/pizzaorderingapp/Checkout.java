package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    ArrayList<String> items;
    Button btnDelivery, btnPickup;
    ArrayList<String> orderHistory;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        btnDelivery = findViewById(R.id.btnDelivery);
        btnPickup = findViewById(R.id.btnPickup);

        //passed items
        Bundle bundle = this.getIntent().getExtras();
        items = (ArrayList<String>)(bundle.getStringArrayList("passData")).clone();
        userName = bundle.getString("name");

        btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passItems = items.toString();

                String type = "pickup";

                SaveOrderClass saveOrderClass = new SaveOrderClass(Checkout.this);
                saveOrderClass.execute(type, userName, passItems);
                Toast.makeText(Checkout.this, "Order Complete!!!",Toast.LENGTH_SHORT).show();
                Intent goMain = new Intent(Checkout.this, MainActivity.class);
                goMain.putExtra("username",userName);
                startActivity(goMain);
            }
        });
    }

    public void Delivery (View v){

        String passItems = items.toString();

        String type = "delivery";

        SaveOrderClass saveOrderClass = new SaveOrderClass(this);
        saveOrderClass.execute(type, userName, passItems);
        Toast.makeText(Checkout.this, "Order Complete!!!",Toast.LENGTH_SHORT).show();
        Intent goMain = new Intent(Checkout.this, MainActivity.class);
        goMain.putExtra("username",userName);
        startActivity(goMain);
    }
}
