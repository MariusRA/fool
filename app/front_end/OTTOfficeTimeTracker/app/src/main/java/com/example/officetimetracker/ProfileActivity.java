package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {

    Button signOut, updateProfile;
    EditText name, email, password, oldPassword;

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

        updateProfile.setEnabled(false);

        TextWatcher profileModifiedWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (usernameValidation() && passValidation() && emailValidation()) {
                    updateProfile.setEnabled(true);
                } else {
                    updateProfile.setEnabled(false);
                }
            }
        };

        name.addTextChangedListener(profileModifiedWatcher);
        email.addTextChangedListener(profileModifiedWatcher);
        password.addTextChangedListener(profileModifiedWatcher);
        oldPassword.addTextChangedListener(profileModifiedWatcher);

        mydbh = new DatabaseHelper(getApplicationContext());
        currentUser = User.getInstance(null, null);

        if (mydbh.loadProfile(currentUser)) {
            name.setText(currentUser.getUsername());
            email.setText(currentUser.getEmail());
            password.setText(currentUser.getPassword());
            oldPassword.setText("password");
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            User.releaseInstance();
            startActivity(intent);

        }

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean uniqueUser = mydbh.checkUniqueUser(name.getText().toString(), email.getText().toString(), currentUser.getId());
                if (uniqueUser) {
                    String oldPass = currentUser.getPassword();
                    if (oldPass.equals(oldPassword.getText().toString())) {
                        Boolean result = mydbh.update(name.getText().toString(), email.getText().toString(), password.getText().toString(), currentUser.getId());
                        if (result) {
                            Toast.makeText(getApplicationContext(), "Update successfull!", Toast.LENGTH_LONG).show();
                            updateProfile.setEnabled(false);
                            User.releaseInstance();
                            currentUser = User.getInstance(name.getText().toString(), password.getText().toString());
                            if (mydbh.loadProfile(currentUser)) {
                                name.setText(currentUser.getUsername());
                                email.setText(currentUser.getEmail());
                                password.setText(currentUser.getPassword());
                                oldPassword.setText("password");
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem updating the profile!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Introduce the old password again!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "A user with this datas already exists!", Toast.LENGTH_LONG).show();
                }

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                User.releaseInstance();
                startActivity(intent);

            }
        });
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
        String oldPassInput=oldPassword.getText().toString();
        if (passInput.isEmpty()) {
            password.setError("Please fill out this field");
            return false;
        }
        if (oldPassInput.isEmpty()) {
            oldPassword.setError("Please fill out this field");
            return false;
        }
        oldPassword.setError(null);
        password.setError(null);
        return true;
    }

}
