package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {

    EditText editName, editPWD, editEmail, editAddress, editPayment;
    Button btnRegister, btnGoback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = (EditText)findViewById(R.id.registerName);
        editPWD = (EditText) findViewById(R.id.registerPsw);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editPayment = (EditText) findViewById(R.id.editPayment);
        btnRegister = findViewById(R.id.btnRegisterConfirm);
        btnGoback = findViewById(R.id.btnGoLogin);

        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(Register.this, LoginActivity.class);
                startActivity(goLogin);
            }
        });
    }

    public void Register (View v) throws ExecutionException, InterruptedException {
        if (editName.getText().length() < 1) {
            Toast.makeText(Register.this, "Please type your username!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editPWD.getText().length() < 1) {
            Toast.makeText(Register.this, "Please type your password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editEmail.getText().length() < 1) {
            Toast.makeText(Register.this, "Please type your Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editAddress.getText().length() < 1) {
            Toast.makeText(Register.this, "Please type your Address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editPayment.getText().length() < 1) {
            Toast.makeText(Register.this, "Please type your Payment!", Toast.LENGTH_SHORT).show();
            return;
        }

        String type = "register";
        String name = editName.getText().toString();
        String pwd = editPWD.getText().toString();
        String email = editEmail.getText().toString();
        String address = editAddress.getText().toString();
        String payment = editPayment.getText().toString();

        if (isOnline()) {
            LoginBackground background = new LoginBackground(Register.this);
            Boolean temp = background.execute(type,name, pwd, email, address, payment).get();
            try {
                if (temp) {
                    Intent goLogin = new Intent(Register.this, LoginActivity.class);
                    Toast.makeText(Register.this, "Register successful!", Toast.LENGTH_SHORT).show();
                    startActivity(goLogin);
                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("error", "Register error, check php file");
                Toast.makeText(Register.this, "Register Failed!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Register.this, "You are not connected to the Internet!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo==null) &&networkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

}
