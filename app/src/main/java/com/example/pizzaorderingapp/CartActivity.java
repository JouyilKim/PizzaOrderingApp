package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    int sum = 0;
    String [] boughtItems=null;

    TextView txtTotal;
    LinearLayout layoutScroll;
    Button btnNxt;

    public ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        txtTotal = findViewById(R.id.txtTotal);
        layoutScroll = findViewById(R.id.layoutScroll);
        btnNxt = findViewById(R.id.btnNxt);

        Bundle bundle =this.getIntent().getExtras();

        items = (ArrayList<String>)(bundle.getStringArrayList("items")).clone();

        /*
        /background thread to make view on the items
         */
        CartBackground cart = new CartBackground();
        cart.execute(items);

        //go to the next activity
        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, Checkout.class);
                intent.putExtra("passData", items);
                startActivity(intent);
            }
        });
    }

    class CartBackground extends AsyncTask <List, String, List> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //calc sum
            for(int i=1; i<(items.size()); i+=2){
                sum += Integer.valueOf(items.get(i));
            }

            //bought items
            boughtItems = new String[items.size()/2];

                    for(int a=0; a<(boughtItems.length);a++) {
                    boughtItems[a] = items.get(a*2);
                }

        }

        @Override
        protected ArrayList doInBackground(List... List) {

            txtTotal.setText("Tatal Price: $"+String.valueOf(sum));

            showItemsBought(boughtItems);

            return null;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
        }
    }

    private void showItemsBought(String [] items){
        for (int i = 0;i<items.length;i++) {
            TextView tvShowItems = new TextView(CartActivity.this);

            tvShowItems.setText(items[i]);
            tvShowItems.setTextSize(25);
            layoutScroll.addView(tvShowItems);

        }
    }
}
