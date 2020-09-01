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

    public boolean insertProject(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        long result = db.insert("projects", null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean update(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where id=?"+ user.getId(), null);
        if (cursor.getCount() == 1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", user.getUsername());
            contentValues.put("email", user.getEmail());
            contentValues.put("password", user.getPassword());
            db.update("users", contentValues, "id=" + user.getId(), null);
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email=?", new String[]{email});
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
        user.setId(cursor.getString(cursor.getColumnIndex("id")));
        return true;
    }

    public Cursor dataView() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select id,name,description from projects", null);
        return cursor;
    }

}
