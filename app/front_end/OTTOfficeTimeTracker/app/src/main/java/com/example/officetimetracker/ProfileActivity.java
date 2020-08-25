package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {

    Button signOut, updateProfile;
    TextView name, email, password, oldPassword;
    String passwordValue;

    User currentUser;
    DatabaseHelper mydbh;

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
        setContentView(R.layout.activity_profile);

        signOut = findViewById(R.id.btnSignOut);
        updateProfile = findViewById(R.id.btnUpdateProfile);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        oldPassword = findViewById(R.id.etOldPassword);

        passwordValue = oldPassword.getText().toString();


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                User.releaseInstance();
                startActivity(intent);

            }
        });

        //here check if fields are empty, check the new email and enable the button if all ok
        updateProfile.setEnabled(false);
        /*if (usernameValidation() && emailValidation() && passValidation()) {
            Boolean mailCheck = mydbh.checkEmail(email.getText().toString());
            if (mailCheck) {
                if (userModified()) {
                    updateProfile.setEnabled(true);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Email already registered!", Toast.LENGTH_LONG).show();
            }
        }*/

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = User.getInstance(null, null);
                currentUser.setUsername(name.getText().toString());
                currentUser.setEmail(email.getText().toString());
                currentUser.setPassword(password.getText().toString());

                mydbh = new DatabaseHelper(getApplicationContext());

                Boolean result = mydbh.update(currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword(), passwordValue);
                if (result) {
                    Toast.makeText(getApplicationContext(), "Update successfull!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Problem updating the profile, check your password and try again!", Toast.LENGTH_LONG).show();
                }
                User.releaseInstance();
            }
        });

        currentUser = User.getInstance(null, null);
        mydbh = new DatabaseHelper(getApplicationContext());
        if (mydbh.loadProfile(currentUser)) {
            name.setText(currentUser.getUsername());
            email.setText(currentUser.getEmail());
            password.setText(currentUser.getPassword());
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            User.releaseInstance();
            startActivity(intent);

        }

    }

    private boolean userModified() {

        String usernameInput = name.getText().toString();
        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        String oldPasswordInput = oldPassword.getText().toString();

        User user;
        user = User.getInstance(null, null);

        if ((!usernameInput.equals(user.getUsername()))
                || (!emailInput.equals(user.getEmail()))
                || (!passwordInput.equals(user.getPassword()))
        ) {
            if (oldPasswordInput.equals(user.getPassword())) {
                user.setUsername(usernameInput);
                user.setEmail(emailInput);
                user.setPassword(passwordInput);
            } else {
                User.releaseInstance();
                return false;
            }
        } else {
            User.releaseInstance();
            return false;
        }
        return true;
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

        String usernameInput = name.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            name.setError("Please fill out this field");
            return false;
        }
        name.setError(null);
        return true;
    }

    private boolean passValidation() {

        String passInput = password.getText().toString().trim();
        if (passInput.isEmpty()) {
            password.setError("Please fill out this field");
            return false;
        }
        password.setError(null);
        return true;
    }

}
