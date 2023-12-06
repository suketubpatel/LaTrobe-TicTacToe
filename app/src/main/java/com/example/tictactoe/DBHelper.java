package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String PLAYERS_TABLE_NAME = "players";

    // constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {   // Create table
        sqLiteDatabase.execSQL("CREATE TABLE " + PLAYERS_TABLE_NAME +
                "(id INTEGER PRIMARY KEY, name TEXT, number_of_moves INTEGER ,date_time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // insert record into table by calling this method
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertPlayer (String name, Integer number_of_moves, LocalDateTime date_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = date_time.format(myFormat);

        contentValues.put("name", name);
        contentValues.put("number_of_moves", number_of_moves);
        contentValues.put("date_time", formattedDate);
        db.insert(PLAYERS_TABLE_NAME, null, contentValues);
        return true;
    }

    // Read data by provided sql queary using this method
    public Cursor getData() {       // get all data in assending of score
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + PLAYERS_TABLE_NAME +
                    " ORDER BY number_of_moves",null);
        } catch (SQLiteException e) {
            Log.e("Error: ", e.getMessage());
        }

        return cursor;
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + PLAYERS_TABLE_NAME);
    }

    // Number of raws in the table
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PLAYERS_TABLE_NAME);
        return numRows;
    }

    // Put data into an ArrayList
    @SuppressLint("Range")
    public ArrayList<String> getAllPlayers() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = getData();          // call get data method

            cursor.moveToFirst();       // move to first record
            while(cursor.isAfterLast() == false) {      // untill the last recor
                arrayList.add(cursor.getString(cursor.getColumnIndex("name")) + " " +
                        cursor.getString(cursor.getColumnIndex("number_of_moves")) + " " +
                        cursor.getString(cursor.getColumnIndex("date_time")));
                cursor.moveToNext();
            }
        } catch (SQLiteException e) {
            Log.e("Error while fatching: ", e.getMessage());
        }

        return arrayList;
    }
}
