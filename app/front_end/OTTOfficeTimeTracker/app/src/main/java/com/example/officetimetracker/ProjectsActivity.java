package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ProjectsActivity extends AppCompatActivity {

    DatabaseHelper mydbh;
    TableLayout table;
    TableRow row;
    Button addProject, deleteProject, editProject, seeProjectInfo;
    EditText projectName, projectDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        table = findViewById(R.id.tlProjects);
        addProject = findViewById(R.id.btnAddProject);
        deleteProject = findViewById(R.id.btnDeleteProject);
        editProject = findViewById(R.id.btnEditProject);
        seeProjectInfo = findViewById(R.id.btnSeeInfo);

        projectName = findViewById(R.id.etProjectName);
        projectDescription = findViewById(R.id.etProjectDescription);

       /* addProject.setEnabled(false);
        deleteProject.setEnabled(false);
        editProject.setEnabled(false);
        seeProjectInfo.setEnabled(false);


        Cursor cursor = mydbh.projectsView();

       if(cursor.getCount()==0){
           Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
       }
       else{
           while(cursor.moveToNext()){
               row=new TableRow(getApplicationContext());
               TextView id=new TextView(getApplicationContext());
               TextView name=new TextView(getApplicationContext());
               TextView description=new TextView(getApplicationContext());

               id.setText(cursor.getInt(cursor.getColumnIndex("id")));
               name.setText(cursor.getString(cursor.getColumnIndex("name")));
               description.setText(cursor.getString(cursor.getColumnIndex("description")));

               row.addView(id);
               row.addView(name);
               row.addView(description);
               table.addView(row);
           }
       }*/

        mydbh = new DatabaseHelper(getApplicationContext());

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pNameNotEmpty() && pDescriptionNotEmpty()) {
                    Boolean check = mydbh.checkExistingProject(projectName.getText().toString());
                    if (check) {
                        Boolean isInserted = mydbh.insertProject(projectName.getText().toString(), projectDescription.getText().toString());
                        if (isInserted) {
                            Toast.makeText(getApplicationContext(), "New project inserted!", Toast.LENGTH_LONG).show();
                            projectName.setText("");
                            projectDescription.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem inserting the project!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "This project already exists!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pNameNotEmpty()) {
                    Boolean check = mydbh.checkExistingProject(projectName.getText().toString());
                    if (check) {
                        Boolean isDeleted = mydbh.deleteProject(projectName.getText().toString());
                        if (isDeleted) {
                            Toast.makeText(getApplicationContext(), "Project" + projectName.getText().toString() + "deleted!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem deleting" + projectName.getText().toString() + "!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Project" + projectName.getText().toString() + "does not exist!", Toast.LENGTH_LONG).show();
                    }
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
