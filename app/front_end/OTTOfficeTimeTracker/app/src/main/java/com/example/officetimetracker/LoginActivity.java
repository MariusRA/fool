package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, pass;
    Button login;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.nameLog);
        pass=findViewById(R.id.passLog);
        login=findViewById(R.id.btnLog);

        preferences=getSharedPreferences("UserInfo",0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue=username.getText().toString();
                String passwordValue=pass.getText().toString();

                String registeredUsername=preferences.getString("Username","");
                String registeredPassword=preferences.getString("Password","");

                if(usernameValue.equals(registeredUsername) && passwordValue.equals(registeredPassword)){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Intent intent =getIntent();
    }

    public void onClickNeedReg(View view){

        Intent intent =new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
