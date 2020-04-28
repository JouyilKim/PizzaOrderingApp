package com.example.pizzaorderingapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;


public class PizzaFragment extends Fragment {

    LinearLayout mainLayout;
    public PizzaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pizza, container, false);
        // Inflate the layout for this fragment

        mainLayout = (LinearLayout) v.findViewById(R.id.layoutPizza);

        return v;

    }

    class LoadProducts extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urlAddress) {
            StringBuilder builder = null;
            try {
                String showAddress = UrlSettings.searchPizza;
                URL url = new URL(showAddress);
                Log.d("Fetch data", "url = "+showAddress);

                URLConnection connection = url.openConnection();
                Log.d("Fetch data", "try url connection");

                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                builder = new StringBuilder();
                String line = null;
                while ((line = bufferReader.readLine()) != null)
                {
                    builder.append(line);
                }

                Log.d("Fetch data", builder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String jsonValue) {
            super.onPostExecute(jsonValue);

            try {
                JSONArray jsonArray = new JSONArray(jsonValue);

                //the main loop to read colume one by one
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final String productType = jsonObject.getString("name");
                    TextView tvName = new TextView(getActivity().getApplicationContext());
                    //tvName
                    //mainLayout.addView(tvName);


                }
            }
            catch(Exception e){

            }
        }
    }
}
