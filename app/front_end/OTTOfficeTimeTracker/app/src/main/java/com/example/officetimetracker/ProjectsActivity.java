package com.example.officetimetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
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
                        //here select the data from within the row
                    }
                });

                // id.setOnClickListener();
                // id.getText();

                //make text views clickable, and create onclick
                //manager
                //verify the manager id with the current user

                //checkboxes

                //function for adding a new row
                //class for Project with fields name id description managerId projectManagerName
                //in constructor: name and managerId

                //edit button->update button
                //delete see info button

                //a new activity for editing a project, pass the id to intent
                //if currentuser id != manager id -> disable all buttons and all checkboxes
                //move the 2 editTexts to the new activity
                //3 buttons: update,delete(active for existing projects),add
                //projects in the tabe->clickable->take the text(project name)->move to the new activity
                //make sure that the name is single word

                //at the end of the table add a "+" button for adding a project->pass -1 as the id

            }
        }



    }

}
