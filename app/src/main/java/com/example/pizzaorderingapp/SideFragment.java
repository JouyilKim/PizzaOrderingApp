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
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class SideFragment extends Fragment {

    LinearLayout mainLayout;
    public SideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pizza, container, false);
        // Inflate the layout for this fragment

        //pass the url to the AsyncTask class
        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute(UrlSettings.searchProducts + "?productType=sides");

        mainLayout = (LinearLayout) v.findViewById(R.id.layoutPizza);
        return v;
    }

    class LoadProducts extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urlAddress) {
            StringBuilder stringBuilder = null;
            try {
                //***************connecttion to the database****************
                Log.d("url check", "url:"+urlAddress[0]);
                URL url = new URL(urlAddress[0]);
                URLConnection connection = url.openConnection();

                //
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferReader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }

                Log.d("Fetch data", "the result: "+stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String jsonValue) {
            super.onPostExecute(jsonValue);

            Log.d("onPost", "onPost execute");
            try {
                JSONArray jsonArray = new JSONArray(jsonValue);

                //the main loop to read colume one by one
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //***************get product information****************
                    String productName = jsonObject.getString("name");
                    String productPrice = "$"+ jsonObject.getString("price");
                    //set to UI
                    TextView tvName = new TextView(getActivity());
                    TextView tvPrice = new TextView(getActivity());
                    tvName.setText(productName);
                    tvPrice.setText(productPrice);
                    mainLayout.addView(tvName);


                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
