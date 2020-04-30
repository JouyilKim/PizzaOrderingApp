package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrderMenu extends FragmentActivity {

    Button btnCart;
    Button btnOderPizza;
    Button btnOrderSides;
    Button btnOrderDrinks;
    Fragment fragment;
    String userName;
    public ArrayList<String> newItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

        btnCart = findViewById(R.id.btnCart);
        btnOderPizza = findViewById(R.id.btnPizzaMenu);
        btnOrderSides = findViewById(R.id.btnSideMenu);
        btnOrderDrinks = findViewById(R.id.btnDrinksMenu);

        Bundle bundle = this.getIntent().getExtras();
        userName = bundle.getString("name");

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newItems.isEmpty()){
                    Toast.makeText(OrderMenu.this, "the Cart is Empty! Add something!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent goCart = new Intent(OrderMenu.this, CartActivity.class);
                    goCart.putStringArrayListExtra("items", newItems);
                    goCart.putExtra("name", userName);
                    startActivity(goCart);
                }
            }
        });
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
