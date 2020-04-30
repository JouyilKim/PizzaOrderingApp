package com.example.pizzaorderingapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class SideFragment extends Fragment {

    private LinearLayout upperLayout;
    private LinearLayout mainLayout;
    private RadioGroup radioGroup;
    private String[] sidesNames= {"fries.png", "rings.png", "hash.png"};
    private int buttonChecked=0;
    private String [][] itemList = new String[sidesNames.length][2];
    public SideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        // Inflate the layout for this fragment
        radioGroup = new RadioGroup(getActivity());
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        //pass the url to the AsyncTask class
        LoadProducts loadProducts = new LoadProducts();
        loadProducts.execute(UrlSettings.searchProducts + "?productType=sides");

        mainLayout = (LinearLayout) v.findViewById(R.id.innerLayout);
        upperLayout = (LinearLayout)v.findViewById(R.id.pizzaMainLayout);
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
                Toast.makeText(getActivity(), "server error, try later", Toast.LENGTH_SHORT).show();
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
                    String productPrice = jsonObject.getString("price");

                    itemList[i][0] = productName;
                    itemList[i][1] = productPrice;

                    //go set the UI in another method
                    setLayout(productName, productPrice, sidesNames[i]);
                }

                //this is for the add button
                Button btnAdd = new Button(getActivity());
                btnAdd.setText("Add to Cart");
                upperLayout.addView(btnAdd);

                //after adding all products in the radiogroup, add it to the linearlayout
                mainLayout.removeAllViews();
                mainLayout.addView(radioGroup);

                //check which radiobutton selected
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRB = (RadioButton)group.findViewById(checkedId);
                        boolean isChecked = checkedRB.isChecked();
                        if(isChecked) {
                            buttonChecked = checkedId;
                        }
                        //Toast.makeText(getActivity(),"selected",Toast.LENGTH_SHORT).show();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!(buttonChecked==0)){
                            addToCart(buttonChecked);
                            Toast.makeText(getActivity(),"Added to the cart!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),"please select one item!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private void setLayout(String name, String price, String imgName){


        radioGroup.clearCheck();

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
        txtPrice.setText("  Price: $" +price);
        txtPrice.setTextSize(20);

        //add images
        /*
        /Picasso is a powerful library which doesn't need asynctask at all but have
        all the function of thread loading and cache deleting
         */
        ImageView pizzaImg = new ImageView(getActivity());
        Picasso.get()
                .load(UrlSettings.imageFolder+imgName)
                .error(R.drawable.progress)
                .placeholder(R.drawable.progress_animation)
                .resize(800,650)
                .into(pizzaImg);

        //add to the group
        radioGroup.addView(rbProduct);
        radioGroup.addView(txtPrice);
        radioGroup.addView(pizzaImg);
    }
    private void addToCart(int buttonChecked){

        //*******important***********
        //because the radiobutton id keep stacking at every fragment refresh, this is a temp way to avoid
        buttonChecked = buttonChecked%3;

        //prepare a string to get pass to the cart
        ((OrderMenu)getActivity()).newItems.add(itemList[buttonChecked-1][0]);
        ((OrderMenu)getActivity()).newItems.add(itemList[buttonChecked-1][1]);


    }
}
