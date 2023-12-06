package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartGame, btnExit, btnHighScore;


    public MainActivity() {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btnStartGame = findViewById(R.id.btnStart);     // Get the button start
            btnStartGame.setOnClickListener(this);          // click event to start game

            btnExit = findViewById(R.id.btnExit);           // Get the button exit
            btnExit.setOnClickListener(this);               // click event to exit

            btnHighScore = findViewById(R.id.btnHighScore); // Show high score on ckick this button
            btnHighScore.setOnClickListener(this);          // click event to high score
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

    }

     @Override
     public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart: {       // Play game on start button clicked
                Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGame);
                break;
            }
            case R.id.btnHighScore: {   // Show high score
                Intent highScore = new Intent(MainActivity.this, HighScoreActivity.class);
                startActivity(highScore);
                break;
            }
            case R.id.btnExit: {        // Exit game
                MainActivity.this.finish();
                System.exit(0);     // end java code and exit
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
                Intent openInfo = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(openInfo);
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}