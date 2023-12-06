package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlayAgain, btnExit, btnDeleteAll;
    DBHelper mydb;
    Cursor cursor;
    ListView list;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_high_score);

            btnPlayAgain = findViewById(R.id.btnPlayAgainHighScore);
            btnPlayAgain.setOnClickListener(this);

            btnExit = findViewById(R.id.btnExitHighScore);
            btnExit.setOnClickListener(this);

            btnDeleteAll = findViewById(R.id.btnDeleteAllRecord);
            btnDeleteAll.setOnClickListener(this);

                    // connect to database
            mydb = new DBHelper(this);

            /*cursor = mydb.getData();
            try {
                if(cursor.moveToFirst()) {
                    do {
                        Log.e("Record: ",
                                cursor.getString(cursor.getColumnIndex("name")) + " " +
                                     cursor.getString(cursor.getColumnIndex("number_of_moves")) + " " +
                                     cursor.getString(cursor.getColumnIndex("date_time")));

                    } while (cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                Log.e("Error: ", e.getMessage());
            }*/

            /*int records = mydb.numberOfRows();
            Log.e("Number of records: ", "" + records);*/

            displayAllRecords();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

    }

    public void displayAllRecords() {
        ArrayList array_list = mydb.getAllPlayers();
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlayAgainHighScore: {      // start game again
                Intent playAgain = new Intent(HighScoreActivity.this, GameActivity.class);
                startActivity(playAgain);
                finish();
                break;
            } case R.id.btnExitHighScore: {           // Close this activity
                finish();
                System.exit(0);
                break;
            } case R.id.btnDeleteAllRecord: {
                mydb.deleteAllRecords();
                displayAllRecords();
                break;
            }
        }
    }

    // following two method handle info menu on current activity
    public boolean onCreateOptionsMenu(Menu menu) {      // display menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent openInfo = new Intent(HighScoreActivity.this, InfoActivity.class);
                startActivity(openInfo);
                break;
        }
        return true;
    }
}
