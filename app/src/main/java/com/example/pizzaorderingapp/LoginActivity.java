package com.example.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editName;
    EditText editPWD;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = (EditText)findViewById(R.id.editName);
        editPWD = (EditText) findViewById(R.id.editPWD);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegisterConfirm);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRegister = new Intent(LoginActivity.this, Register.class);
                startActivity(goRegister);
            }
        });
    }

    public void Login (View v) throws ExecutionException, InterruptedException {

        if(editName.getText().length()<1){
            Toast.makeText(LoginActivity.this, "Please type your username",Toast.LENGTH_SHORT).show();
            return;
        }
        if(editPWD.getText().length()<1){
            Toast.makeText(LoginActivity.this, "Please type your password",Toast.LENGTH_SHORT).show();
            return;
        }

        String type = "login";
        String name = editName.getText().toString();
        String pwd = editPWD.getText().toString();

        if(isOnline()) {
            LoginBackground background = new LoginBackground(this);
            Boolean temp = background.execute(type,name,pwd).get();
            if (temp) {
                Intent goMain = new Intent(LoginActivity.this, MainActivity.class);
                goMain.putExtra("username", temp);
                startActivity(goMain);
            } else {
                Toast.makeText(LoginActivity.this, "Input wrong! try again!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "You are not connected to the Internet!", Toast.LENGTH_SHORT).show();
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
