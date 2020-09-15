package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProjectActivity extends AppCompatActivity {

    Button addProject,deleteProject,updateProject;
    EditText projectName,projectDescription;
    TextView managerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        addProject=findViewById(R.id.btnAddProject);
        deleteProject=findViewById(R.id.btnDeleteProject);
        updateProject=findViewById(R.id.btnAddProject);
        projectName=findViewById(R.id.etProjectName);
        projectDescription=findViewById(R.id.etProjectDescription);
        managerName=findViewById(R.id.tvManagerName);









    }

    private boolean pNameNotEmpty() {

        String pNameInput = projectName.getText().toString().trim();
        if (pNameInput.isEmpty()) {
            projectName.setError("Please fill out this field");
            return false;
        }
        projectName.setError(null);
        return true;
    }

    private boolean pDescriptionNotEmpty() {

        String pDescriptionInput = projectDescription.getText().toString().trim();
        if (pDescriptionInput.isEmpty()) {
            projectDescription.setError("Please fill out this field");
            return false;
        }
        projectDescription.setError(null);
        return true;
    }
}
