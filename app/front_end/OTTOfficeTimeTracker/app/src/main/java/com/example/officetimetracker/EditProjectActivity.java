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
    Project project, prj;

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
        mydbh.loadProfile(user);

        String receivedID = getIntent().getExtras().getString("clicked");

        assert receivedID != null;
        if (Integer.parseInt(receivedID) != -1) {

            project = new Project(receivedID);

            if (mydbh.loadProject(project)) {
                projectName.setText(project.getName());
                projectDescription.setText(project.getDescription());
                managerName.setText(mydbh.projectManager(project.getManagerId()));
            }
            updateProject.setEnabled(true);
            deleteProject.setEnabled(true);
            addProject.setEnabled(false);
            if (user.getId() != project.getManagerId()) {
                updateProject.setEnabled(false);
                deleteProject.setEnabled(false);
            }
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

                boolean projectNotAlreadyExists = mydbh.checkExistingProject(projectName.getText().toString().trim());
                if (projectNotAlreadyExists) {
                    prj = new Project(projectName.getText().toString().trim(), projectDescription.getText().toString());
                    prj.setManagerId(user.getId());
                    boolean isInserted = mydbh.insertProject(prj);
                    if (isInserted) {
                        Toast.makeText(getApplicationContext(), "New project added!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Problem adding the new project!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "A project with this name already exists!", Toast.LENGTH_LONG).show();
                }

            }
        });

        updateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean projectNotAlreadyExists = mydbh.checkExistingProject(projectName.getText().toString().trim());
                if (projectNotAlreadyExists) {
                    Project p = new Project(projectName.getText().toString().trim(), projectDescription.getText().toString());
                    p.setManagerId(project.getManagerId());
                    boolean upProject = mydbh.updateProject(p, String.valueOf(project.getId()));
                    if (upProject) {
                        Toast.makeText(getApplicationContext(), "Project updated!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Project updated!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This project already exists!", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isDeleted = mydbh.deleteProject(project);
                if (isDeleted) {
                    Toast.makeText(getApplicationContext(), "Project successfully deleted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Problem deleting the project!", Toast.LENGTH_LONG).show();
                }

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
