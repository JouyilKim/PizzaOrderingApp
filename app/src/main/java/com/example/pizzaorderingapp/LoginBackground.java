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

public class LoginBackground extends AsyncTask <String, Void, Boolean>{
    Context cxt;
    public LoginBackground(Context context){
        cxt = context;
    }

    @Override
    protected Boolean doInBackground(String...params) {
        boolean temp = false;
        String type = params[0];
        String loginUrlAddress = UrlSettings.userSelect;
        String registerUrlAddress = UrlSettings.userRegister;

        if (type.equals("login")) {
            try {
                String name = params[1];
                String pwd = params[2];
                //***************connecttion to the database****************
                Log.d("url check", "url:" + loginUrlAddress);
                URL url = new URL(loginUrlAddress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                /////////////////////////script/////////////
                String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
                        "&" + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");

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
                if (result.equals("login success")) {
                    Log.d("success", "login success");
                    temp = true;
                }
                return temp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")){
            try {
                String name = params[1];
                String pwd = params[2];
                String email = params[3];
                String address = params[4];
                String payment = params[5];

                //***************connecttion to the database****************
                Log.d("url check", "url:" + registerUrlAddress);
                URL url = new URL(registerUrlAddress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                /////////////////////////script/////////////
                String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
                        "&" + URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8") +
                        "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                        "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") +
                        "&" + URLEncoder.encode("payment", "UTF-8") + "=" + URLEncoder.encode(payment, "UTF-8");

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
                    Log.d("success", "register success");
                    temp = true;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

    }
}
