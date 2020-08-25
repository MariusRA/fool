package com.example.officetimetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Worktime.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key autoincrement,username text,email text,password text)");
        db.execSQL("create table projects(id integer primary key autoincrement,managerId integer,name text,description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists projects");
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

    public boolean update(String newUsername, String newEmail, String newPassword, String oldPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username=? and password=?", new String[]{oldPassword});
        if (cursor.getCount() == 1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", newUsername);
            contentValues.put("email", newEmail);
            contentValues.put("password", newPassword);
            db.update("users", contentValues, "password=?", new String[]{oldPassword});
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
        return true;
    }

    public Cursor dataView() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select id,name,description from projects",null);
        return cursor;
    }

}
