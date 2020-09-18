package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ProjectsActivity extends AppCompatActivity {

    DatabaseHelper mydbh;
    TableLayout table;
    TextView hId, hName, hDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        table = findViewById(R.id.tlProjects);
        hId = findViewById(R.id.etPID);
        hName = findViewById(R.id.etPName);
        hDescription = findViewById(R.id.etPDescription);

        mydbh = new DatabaseHelper(getApplicationContext());
        Cursor cursor = mydbh.projectsView();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No projects at the moment", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);
                row.setClickable(true);

                TextView id, name, description;
                id = new TextView(this);
                name = new TextView(this);

                description = new TextView(this);

                id.setLayoutParams(hId.getLayoutParams());
                name.setLayoutParams(hName.getLayoutParams());
                description.setLayoutParams(hDescription.getLayoutParams());

                id.setGravity(Gravity.CENTER);
                name.setGravity(Gravity.CENTER);
                description.setGravity(Gravity.CENTER);

                id.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
                name.setText(cursor.getString(cursor.getColumnIndex("name")));
                description.setText(cursor.getString(cursor.getColumnIndex("description")));

                row.addView(id);
                row.addView(name);
                row.addView(description);
                table.addView(row);

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        v.setBackgroundColor(Color.GRAY);
                        TableRow tablerow = (TableRow) v;
                        TextView pID = (TextView) tablerow.getChildAt(0);
                        String currentID = pID.getText().toString();

                        Intent intent = new Intent(getApplicationContext(), EditProjectActivity.class);
                        intent.putExtra("clicked", currentID);
                        startActivity(intent);
                    }
                });

            }

        }
        TextView addProject = new TextView(this);
        addProject.setClickable(true);
        addProject.setTextSize(15);
        addProject.setText("Add a new project");
        TableRow tRow = new TableRow(this);
        tRow.addView(addProject);
        table.addView(tRow);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProjectActivity.class);
                intent.putExtra("clicked", String.valueOf(-1));
                startActivity(intent);
            }
        });


    }

}
