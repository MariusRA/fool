package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProjectActivity extends AppCompatActivity {

    Button addProject, deleteProject, updateProject;
    EditText projectName, projectDescription;
    TextView managerName;
    DatabaseHelper mydbh;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        addProject = findViewById(R.id.btnAddProject);
        deleteProject = findViewById(R.id.btnDeleteProject);
        updateProject = findViewById(R.id.btnUpdateProject);
        projectName = findViewById(R.id.etProjectName);
        projectDescription = findViewById(R.id.etProjectDescription);
        managerName = findViewById(R.id.tvManagerName);

        mydbh = new DatabaseHelper(getApplicationContext());
        user = User.getInstance(null, null);

        String receivedID = getIntent().getExtras().getString("clicked");

        assert receivedID != null;
        if (Integer.parseInt(receivedID) != -1) {

            Project project = new Project(receivedID);

            if (mydbh.loadProject(project)) {
                projectName.setText(project.getName());
                projectDescription.setText(project.getDescription());
                managerName.setText(String.valueOf(project.getManagerId()));
            }
            updateProject.setEnabled(true);
            deleteProject.setEnabled(true);
            addProject.setEnabled(false);
        } else {
            if (Integer.parseInt(receivedID) == -1) {

                projectName.setText("");
                projectDescription.setText("");
                managerName.setText(user.getUsername());

                deleteProject.setEnabled(false);
                updateProject.setEnabled(false);
                addProject.setEnabled(false);

                TextWatcher introducedDataForNewProject = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (pNameNotEmpty() && pDescriptionNotEmpty()) {
                            addProject.setEnabled(true);
                        } else {
                            addProject.setEnabled(false);
                        }
                    }
                };
                projectName.addTextChangedListener(introducedDataForNewProject);
                projectDescription.addTextChangedListener(introducedDataForNewProject);
            }
        }

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        updateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
