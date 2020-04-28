package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class OrderMenu extends FragmentActivity {

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

        //initial fragment (pizza)
        fragment = new PizzaFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FoodFragment, fragment);
        ft.commit();
    }

    //switch fragment between pizza, sides, and drinks
    public void FragmentSelect(View v){
        LinearLayout linearLayoutl = (LinearLayout) findViewById(R.id.FoodFragment);
        linearLayoutl.removeAllViews();

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
