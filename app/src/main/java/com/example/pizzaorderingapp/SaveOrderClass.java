package com.example.pizzaorderingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SaveOrderClass extends AsyncTask<String, Void, Boolean> {
    Context cxt;
    public SaveOrderClass(Context context){
        cxt = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean temp = false;
        String type = params[0];
        String name = params[1];
        String orders = params[2];
        String orderUrl = UrlSettings.orderUrl;

            try {
                //***************connecttion to the database****************
                Log.d("url check", "url:" + orderUrl);
                URL url = new URL(orderUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                /////////////////////////script/////////////
                String post_data = URLEncoder.encode("deliveryType", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") +
                        "&" + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
                        "&" + URLEncoder.encode("orderHistory", "UTF-8") + "=" + URLEncoder.encode(orders, "UTF-8");

                Log.d("dd", post_data);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                Log.d("Fetch data", "the result: " + result);
                if (result.equals("success")) {
                    Log.d("success", "login success");
                    temp = true;
                }
                return temp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return temp;
    }
}
