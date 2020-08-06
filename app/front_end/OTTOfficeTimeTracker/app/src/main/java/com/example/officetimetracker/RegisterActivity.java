package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText email, username,pass;
    Button register;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.emailReg);
        username=findViewById(R.id.nameReg);
        pass=findViewById(R.id.passReg);
        register=findViewById(R.id.btnSubmitReg);

        preferences=getSharedPreferences("UserInfo",0);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String emailValue=email.getText().toString();
                String userValue=username.getText().toString();
                String passValue=pass.getText().toString();

                if(userValue.length()>1 && emailValue.length()>1 && passValue.length()>1) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Email", emailValue);
                    editor.putString("Username", userValue);
                    editor.putString("Password", passValue);
                    editor.apply();
                    Toast.makeText(RegisterActivity.this,"User Registered!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Fill all fields for registration",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Intent intent =getIntent();
    }

    public void onClickAlreadyReg(View view){

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
