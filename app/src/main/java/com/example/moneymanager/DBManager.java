package com.example.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DBManager {

    //логика БД, вставка получение, обновление удаление данных

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertUserIntoDB(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.USER_USERNAME, username);
        values.put(Constants.USER_PASSWORD, password);
        values.put(Constants.USER_CURRENCY, "BYN");

        db.insert(Constants.USER_TABLE_NAME, null, values);
    }

    public void insertNoteIntoDB(double price, String userId,String category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.NOTE_SUM, Double.toString(price));
        values.put(Constants.NOTE_USER, userId);
        values.put(Constants.NOTE_CATEGORY, category);
        LocalDateTime now = LocalDateTime.now();
        values.put(Constants.NOTE_DATE, Storage.dateTimeFormatter.format(now));
        db.insert(Constants.NOTE_TABLE_NAME, null, values);
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorUsers = db.rawQuery("SELECT * FROM " + Constants.USER_TABLE_NAME, null);
        ArrayList<User> users = new ArrayList<>();

        if (cursorUsers.moveToFirst()) {
            do {
                users.add(new User(cursorUsers.getInt(0), cursorUsers.getString(1), cursorUsers.getString(2),cursorUsers.getString(3)));
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return users;
    }

    public ArrayList<Note> getNotes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorNotes = db.rawQuery("SELECT * FROM " + Constants.NOTE_TABLE_NAME, null);
        ArrayList<Note> notes = new ArrayList<>();

        if (cursorNotes.moveToFirst()) {
            do {
                notes.add(new Note(cursorNotes.getInt(0), cursorNotes.getDouble(1),cursorNotes.getString(2),cursorNotes.getString(3), cursorNotes.getString(4)));
            } while (cursorNotes.moveToNext());
        }
        cursorNotes.close();
        Collections.reverse(notes);
        return notes;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.NOTE_SUM, note.getPrice());
        cv.put(Constants.NOTE_CATEGORY, note.getCategory());
        cv.put(Constants.NOTE_USER, note.getUserId());
        db.update(Constants.NOTE_TABLE_NAME, cv, "_id = ?", new String[]{Integer.toString(note.getId())});
    }

    public void updateUserCurrency(User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.USER_CURRENCY, user.getCurrency());
        db.update(Constants.USER_TABLE_NAME, cv, "_id = ?", new String[]{Integer.toString(user.getId())});

    }

    public void deleteNote(Note note) {
        String[] args = {Integer.toString(note.getId())};
        dbHelper.getWritableDatabase().delete(Constants.NOTE_TABLE_NAME, Constants.USER_ID + "=?", args);
    }

    public void closeDB() {
        dbHelper.close();
    }

    public void deleteAllUsers() {
        db.delete(Constants.USER_TABLE_NAME, null, null);
    }
}
