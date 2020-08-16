package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, pass;
    Button login;

    DatabaseHelper mydbh=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.nameLog);
        pass=findViewById(R.id.passLog);
        login=findViewById(R.id.btnLog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean usernamePassCheck=mydbh.checkUsernamePass(username.getText().toString(),pass.getText().toString());
                if(usernamePassCheck) {
                    if (usernameValidation() && passValidation()) {
                        Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect Username or Password!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onClickNeedReg(View view){

        Intent intent =new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean usernameValidation() {

        String usernameInput = username.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            username.setError("Please fill out this field");
            return false;
        }
        username.setError(null);
        return true;
    }

    private boolean passValidation() {

        String passInput = pass.getText().toString().trim();
        if (passInput.isEmpty()) {
            pass.setError("Please fill out this field");
            return false;
        }
        pass.setError(null);
        return true;
    }

}
