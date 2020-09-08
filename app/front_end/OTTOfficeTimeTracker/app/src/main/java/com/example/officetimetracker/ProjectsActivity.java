package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class ProjectsActivity extends AppCompatActivity {

    DatabaseHelper mydbh;
    TableLayout table;
    TableRow row;
    Button addProject,deleteProject,editProject,seeProjectInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        table=findViewById(R.id.tlProjects);
        addProject=findViewById(R.id.btnAddProject);
        deleteProject=findViewById(R.id.btnDeleteProject);
        editProject=findViewById(R.id.btnEditProject);
        seeProjectInfo=findViewById(R.id.btnSeeInfo);



    }
}
