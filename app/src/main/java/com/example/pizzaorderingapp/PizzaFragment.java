package com.example.pizzaorderingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLEncoder;


public class PizzaFragment extends Fragment {

    LinearLayout mainLayout;
    RadioGroup radioGroup;
    String[] pizzaNames= {"pizzaComb.jpg", "pizzaVege.png", "pizzaMeat.png"};
    Bitmap [] pizzaImg= new Bitmap[pizzaNames.length];
    public PizzaFragment() {
        // Required empty public constructor
    }

    //I found it is no need to use onCreate in fragment. results are the same

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pizza, container, false);
        // Inflate the layout for this fragment

        //create new RadioGroup in for the menu
        radioGroup = new RadioGroup(getActivity());
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        //pass the url to the AsyncTask class
        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute(UrlSettings.searchProducts + "?productType=pizza");

        mainLayout = (LinearLayout) v.findViewById(R.id.layoutPizza);
        return v;

    }

/*
/class for fetching data from server
 */
    class LoadProducts extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urlAddress) {
            StringBuilder stringBuilder = null;
            try {
                //***************connecttion to the database****************
                Log.d("url check", "url:"+urlAddress[0]);
                URL url = new URL(urlAddress[0]);
                URLConnection connection = url.openConnection();

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

                    //go set the UI in another method
                    setLayout(productName, productPrice, pizzaNames[i]);
                }

                //after adding all products in the radiogroup, add it to the linearlayout
                mainLayout.removeAllViews();
                mainLayout.addView(radioGroup);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    //set the product information on the xml file
    private void setLayout(String name, String price, String imgName){


        //make new textviews
        RadioButton rbProduct = new RadioButton(getActivity());
        TextView txtPrice = new TextView(getActivity());

        //setting layout params below

        //product name
        LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        rbProduct.setLayoutParams(lp);
        rbProduct.setText(name);
        rbProduct.setTextSize(20);

        //product price
        lp.gravity = Gravity.CENTER;
        txtPrice.setLayoutParams(lp);
        txtPrice.setText("  Price: " +price);
        txtPrice.setTextSize(20);

        //add images
        /*
        /Picasso is a powerful library which doesn't need asynctask at all but have
        all the function of thread loading and cache deleting
         */
        ImageView pizzaImg = new ImageView(getActivity());
        Picasso.get()
                .load(UrlSettings.pizzaImgs+imgName)
                .error(R.drawable.progress)
                .placeholder(R.drawable.progress_animation)
                .resize(800,800)
                .into(pizzaImg);

        //add to the group
        radioGroup.addView(rbProduct);
        radioGroup.addView(txtPrice);
        radioGroup.addView(pizzaImg);

    }
}
