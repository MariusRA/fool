package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProjectActivity extends AppCompatActivity {

    Button addProject, deleteProject, updateProject;
    EditText projectName, projectDescription;
    TextView managerName;
    DatabaseHelper mydbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        addProject = findViewById(R.id.btnAddProject);
        deleteProject = findViewById(R.id.btnDeleteProject);
        updateProject = findViewById(R.id.btnAddProject);
        projectName = findViewById(R.id.etProjectName);
        projectDescription = findViewById(R.id.etProjectDescription);
        managerName = findViewById(R.id.tvManagerName);

        String receivedID = getIntent().getExtras().getString("clicked");

        Toast.makeText(getApplicationContext(),receivedID,Toast.LENGTH_LONG).show();

        /*Project project = new Project(receivedID);

        mydbh = new DatabaseHelper(getApplicationContext());

        if (mydbh.loadProject(project)) {
            projectName.setText(project.getName());
            projectDescription.setText(project.getDescription());
            managerName.setText(String.valueOf(project.getManagerId()));
        }*/

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
