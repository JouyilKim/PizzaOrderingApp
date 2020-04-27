package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class OrderMenu extends AppCompatActivity {

    Button btnOderPizza;
    Button btnOrderSides;
    Button btnOrderDrinks;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        btnOderPizza = findViewById(R.id.btnPizzaMenu);
        btnOrderSides = findViewById(R.id.btnSideMenu);
        btnOrderDrinks = findViewById(R.id.btnDrinksMenu);

        //initial fragment
        fragment = new PizzaFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FoodFragment, fragment);
        ft.commit();
        /*
        /listeners to pass food type to the next activity: ShowFoods
         */
        /*
        btnOderPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goOrderPizza = new Intent(OrderMenu.this,ShowFoods.class);
                goOrderPizza.putExtra("foodType", "pizza");
                startActivity(goOrderPizza);
            }
        });

        btnOrderSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goOrderSides = new Intent(OrderMenu.this, ShowFoods.class);
                goOrderSides.putExtra("foodType", "sides");
                startActivity(goOrderSides);
            }
        });

        btnOrderDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goOrderDrinks = new Intent(OrderMenu.this, ShowFoods.class);
                goOrderDrinks.putExtra("foodType", "drinks");
                startActivity(goOrderDrinks);
            }
        });

         */
    }

    public void FragmentSelect(View v){
        FrameLayout fl = (FrameLayout) findViewById(R.id.FoodFragment);
        fl.removeAllViews();

        if(v ==findViewById(R.id.btnPizzaMenu)){
            fragment = new PizzaFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FoodFragment, fragment);
            ft.commit();
        }
        if(v ==findViewById(R.id.btnSideMenu)){
            fragment =new SideFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FoodFragment, fragment);
            ft.commit();
        }
        if(v ==findViewById(R.id.btnDrinksMenu)){
            fragment = new DrinksFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FoodFragment, fragment);
            ft.commit();
        }
    }
}
