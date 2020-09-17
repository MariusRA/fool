package com.example.officetimetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Worktime.db";
    public static final int DATABASE_VERSION = 2;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key autoincrement,username text,email text,password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL("create table projects(id integer primary key autoincrement,managerId integer,name text,description text)");
        }
    }

    public boolean insert(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean update(String username, String email, String password, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        int result = db.update("users", contentValues, "id=?", new String[]{String.valueOf(id)});
        if (result == 1) {
            return true;
        }
        return false;
    }

    public boolean checkUniqueUser(String username, String email, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username=? and email=? and id!=?", new String[]{username, email, String.valueOf(id)});
        if (cursor.getCount() > 0) {
            return false;
        }
        return true;
    }

    public boolean checkUsernamePass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() == 1) {
            return true;
        }
        return false;
    }

    public boolean loadProfile(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username=? and password=?", new String[]{user.getUsername(), user.getPassword()});
        if (cursor.getCount() < 1) {
            return false;
        }
        cursor.moveToFirst();
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setId(cursor.getInt(cursor.getColumnIndex("id")));
        return true;
    }

    public Cursor projectsView() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select id,name,description from projects", null);
        return cursor;
    }

    public boolean checkExistingProject(String projectName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from projects where name=?", new String[]{projectName});
        if (cursor.getCount() > 0) {
            return false;
        }
        return true;
    }

    public boolean insertProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", project.getName());
        contentValues.put("description", project.getDescription());
        contentValues.put("managerId", project.getManagerId());
        long result = db.insert("projects", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean deleteProject(String projectName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("projects", "name=?", new String[]{projectName});
        if (result == 1) {
            return true;
        }
        return false;
    }

    public boolean loadProject(Project project) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from projects where id=?", new String[]{String.valueOf(project.getId())});
        if (cursor.getCount() < 1) {
            return false;
        }
        cursor.moveToFirst();
        project.setName(cursor.getString(cursor.getColumnIndex("name")));
        project.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        project.setManagerId(cursor.getInt(cursor.getColumnIndex("managerId")));
        return true;
    }

}
