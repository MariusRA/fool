package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectsActivity extends AppCompatActivity {

    DatabaseHelper mydbh;
    TextView projectsList;

    EditText pName, pDescription;
    Button addProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        projectsList = findViewById(R.id.tProjectList);
        pName = findViewById(R.id.projectName);
        pDescription = findViewById(R.id.projectDescription);
        addProject = findViewById(R.id.btnAddProject);

        mydbh = new DatabaseHelper(getApplicationContext());

        StringBuilder stringBuilder = new StringBuilder();

        Cursor cursor = mydbh.dataView();

        if (cursor.getCount() < 1) {
            stringBuilder.append("No data ");
        } else {
            stringBuilder.append("");
            while (cursor.moveToNext()) {
                stringBuilder.append("\nProject id: " + cursor.getInt(cursor.getColumnIndex("id")) +
                        "\nProject name: " + cursor.getString(cursor.getColumnIndex("name")) +
                        "\nProject description: " + cursor.getString(cursor.getColumnIndex("description"))
                        + "\n");
            }
        }
        projectsList.setText(stringBuilder);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mydbh.insertProject(pName.getText().toString(), pDescription.getText().toString());
                if (result) {
                    Toast.makeText(getApplicationContext(), "New project added!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Problem adding the new project!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
