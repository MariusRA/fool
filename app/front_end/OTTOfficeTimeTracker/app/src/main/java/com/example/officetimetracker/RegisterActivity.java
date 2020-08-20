package com.example.officetimetracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText email, username, pass;
    Button register;

    DatabaseHelper mydbh;

    //regular expression/regex for a usual email address
    public static final Pattern EMAIL_ADDRESS = Pattern.compile(

            "[a-zA-z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-z0-9\\-]{0,25}" +
                    ")+"

    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailReg);
        username = findViewById(R.id.nameReg);
        pass = findViewById(R.id.passReg);
        register = findViewById(R.id.btnSubmitReg);

        mydbh = new DatabaseHelper(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usernameValidation() && emailValidation() && passValidation()) {
                    Boolean mailCheck = mydbh.checkEmail(email.getText().toString());
                    if (mailCheck) {
                        boolean isInserted = mydbh.insert(username.getText().toString(), email.getText().toString(), pass.getText().toString());
                        if (isInserted) {
                            Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem at registration!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email already registered!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void onClickAlreadyReg(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean emailValidation() {

        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Please fill out this field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
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
