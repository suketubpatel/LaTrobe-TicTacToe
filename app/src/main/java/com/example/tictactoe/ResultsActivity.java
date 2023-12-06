package com.example.tictactoe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlayAgain, btnExit, btnSave;
    TextView text;
    Bundle bundle;
    String lastWinner, winnerName, player1, player2;
    boolean isComputer, matchDraw, multiPlayer;
    int numOfMoves, currentPlayer;

    DBHelper mydb;
    LocalDateTime myDate;

    public ResultsActivity(){
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_results);

            mydb = new DBHelper(this);              // Create database
            myDate = LocalDateTime.now();                  // current date and time

            // read values from game activity
            bundle = getIntent().getExtras();
            lastWinner = bundle.getString("lastWinner");         // read lastwinner
            isComputer = bundle.getBoolean("isComputer");        // read isComputer
            numOfMoves = bundle.getInt("numOfMoves");            // read number of moves
            matchDraw = bundle.getBoolean("matchDraw");          // read does match draw
            multiPlayer = bundle.getBoolean("multiPlayer");      // does two player playing
            currentPlayer = bundle.getInt("currentPlayer");
            player1 = bundle.getString("player1");
            player2 = bundle.getString("player2");

            btnPlayAgain = findViewById(R.id.btnPlayAgainResult);
            btnPlayAgain.setOnClickListener(this);

            btnExit = findViewById(R.id.btnExitResult);
            btnExit.setOnClickListener(this);

            btnSave = findViewById(R.id.btnSave);
            btnSave.setOnClickListener(this);

            text = findViewById(R.id.textEditName);
            text.setOnClickListener(this);

            displayWinner();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

    }

    // display winner
    public void displayWinner() {
        Log.e("matchDraw", ": " +matchDraw);
        TextView result = findViewById(R.id.textResult);
        TextView move = findViewById(R.id.textResultMove);
        if(!matchDraw) {
            if(!isComputer) {       // if computer is not a winner
                result.setText(lastWinner + " You Win!");
                move.setText("You took " + numOfMoves + " moves to finish the game.");
                winnerName = lastWinner;
                findViewById(R.id.btnSave).setEnabled(true);
            } else {
                result.setText("Computer is the winner!");   // if computer is winner
                move.setText("Computer took " + numOfMoves + " moves to finish the game.");
            }
        } else {
            result.setText("It's a Tie!");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent playAgain = new Intent(ResultsActivity.this, GameActivity.class);
        Intent goHome = new Intent(ResultsActivity.this, MainActivity.class);

        switch (view.getId()) {
            case R.id.btnPlayAgainResult: {      // start game again
                playAgain.putExtra("winnerName", winnerName);
                playAgain.putExtra("multiPlayer",multiPlayer);
                playAgain.putExtra("currentPlayer", currentPlayer);
                playAgain.putExtra("player1", player1);
                playAgain.putExtra("player2", player2);
                ResultsActivity.this.finish();
                startActivity(playAgain);
                break;
            }
            case R.id.btnExitResult: {           // Close this activity
                //playAgain.putExtra("finishGame", true);
                ResultsActivity.this.finish();
                startActivity(goHome);
                System.exit(0);
                break;
            } case R.id.btnSave: {
                Log.e("message: ", "save clicked");
                TextView textEdit = findViewById(R.id.textEditName);
                winnerName = textEdit.getText().toString();
                playAgain.putExtra("winnerName", winnerName);
                playAgain.putExtra("multiPlayer",multiPlayer);
                playAgain.putExtra("currentPlayer", currentPlayer);
                playAgain.putExtra("player1", player1);
                playAgain.putExtra("player2", player2);

                try {
                    mydb.insertPlayer(winnerName, numOfMoves, myDate);
                } catch (Exception e) {
                    Log.e("Error Inserting: ", e.getMessage());
                }
                ResultsActivity.this.finish();
                startActivity(playAgain);
            } case R.id.textEditName: {
                text.setText("");   // clear the text
                Log.e("message", "cleared");
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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
                Intent openInfo = new Intent(ResultsActivity.this, InfoActivity.class);
                startActivity(openInfo);
                break;
        }
        return true;
    }
}
