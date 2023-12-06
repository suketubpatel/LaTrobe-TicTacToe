package com.example.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;



public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnHome, btnEndGame, switchMultiPlayer;      // Button holder
    ImageView imageViews[] = new ImageView[9];          // 9 image view holder
    boolean multiPlayer = false;                        // Does game is playing by two humans
    boolean gameActive = true;                          // continew game
    int counter = 0;                                    // image tap counter
    String lastWinner = null;                           // winner of the last game is continew playing
    int currentPlayer = 1, newCurrentPlayer;            // track the current player
    int numOfMovesP1 = 0, numOfMovesP2 = 0;             // number of moves
    boolean isComputer = false;                         // if it is computers turn
    Bundle bundle;
    Intent displayResult;

    String player1 = "Player1", player2 = "Player2";    // Players name
    String newPlayer1, newPlayer2;                      // User entered players name

    int locationContain[] = new int[9];     // digit in each image location
    // 0 is for 0
    // 1 is for X
    // 2 is nor nothing

    // this are the winning possition
    int winningLocation[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    public GameActivity() {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            /*Log.e("onCreate()", "yes");
            Log.e("LastWinner", ": " + lastWinner);*/

            try {           // Get data from the ResultActivity
                bundle = getIntent().getExtras();
                lastWinner = bundle.getString("winnerName");

                multiPlayer = bundle.getBoolean("multiPlayer");
                findViewById(R.id.switchMultiPlayers).setEnabled(false);

                newPlayer1 = bundle.getString("player1");
                newPlayer2 = bundle.getString("player2");

                newCurrentPlayer = bundle.getInt("currentPlayer");
                // if player one was the winner
                if (newCurrentPlayer == 1) {
                    player1 = lastWinner;
                    currentPlayer = 1;              // he is current first player
                } else {
                    player1 = newPlayer1;
                }

                if (newCurrentPlayer == 2) {        // if player two was the winner
                    player2 = lastWinner;
                    currentPlayer = 2;              // he is current first player
                } else {
                    player2 = newPlayer2;
                }

                if(newCurrentPlayer == 0){          // if match was tie
                    player1 = newPlayer1;
                    currentPlayer = 1;
                    player2 = newPlayer2;
                }


            } catch (Exception e) {
                Log.e("Error", ": " + e.getMessage());
            }


            for(int i = 0; i < 9; i++) {
                locationContain[i] = 2;         // set 2 at each position
            }

            btnHome = findViewById(R.id.btnHome);       // to go to home paage
            btnHome.setOnClickListener(this);

            btnEndGame = findViewById(R.id.btnEnd);     // to end game
            btnEndGame.setOnClickListener(this);

            switchMultiPlayer = findViewById(R.id.switchMultiPlayers);
            switchMultiPlayer.setOnClickListener(this);

            // Get all imageView into array of imageviews holder
            for(int i = 0; i < 9; i++) {
                imageViews[i] = findViewById(R.id.imageView + (i+1));
                imageViews[i].setOnClickListener(this);
            }

            displayPlayer();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    public void resetGame() {               // reset the game vaiables to default
        finish();
        startActivity(getIntent());
        switchMultiPlayer.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHome: {        // retrun to home screen by ending game
                Intent goHome = new Intent(GameActivity.this, MainActivity.class);
                GameActivity.this.finish();
                startActivity(goHome);
                break;
            }
            case R.id.btnEnd: {
                //resetGame();
                if (gameActive)
                    resetGame();                        // reset game
                else
                    startActivity(displayResult);       // display result
                break;
            }
            case R.id.switchMultiPlayers: {     // two human are playing or not
                if(!multiPlayer) {
                    multiPlayer = true;
                    switchMultiPlayer.setText("Multi Players! - Yes");
                    switchMultiPlayer.setTextColor(Color.parseColor("#4CAF50"));
                } else {
                    multiPlayer = false;
                    switchMultiPlayer.setText("Multi Players! - No");
                    switchMultiPlayer.setTextColor(Color.parseColor("#E91E63"));
                }
                break;
            }
            case R.id.imageView1: {         // on image click
                setImages(0);
                break;
            }
            case R.id.imageView2: {         // on image click
                setImages(1);
                break;
            }
            case R.id.imageView3: {         // on image click
                setImages(2);
                break;
            }
            case R.id.imageView4: {         // on image click
                setImages(3);
                break;
            }
            case R.id.imageView5: {         // on image click
                setImages(4);
                break;
            }
            case R.id.imageView6: {         // on image click
                setImages(5);
                break;
            }
            case R.id.imageView7: {         // on image click
                setImages(6);
                break;
            }
            case R.id.imageView8: {         // on image click
                setImages(7);
                break;
            }
            case R.id.imageView9: {         // on image click
                setImages(8);
                break;
            }
        }

        displayPlayer();
    }

    public void displayPlayer() {
        if(gameActive && counter < 9) {    // if game is not finished yet
            TextView txtMessage = findViewById(R.id.txtPlayerTurn);         // get the textbox
            if (multiPlayer) {
                //switchMultiPlayer = findViewById(R.id.switchMultiPlayers);

                switchMultiPlayer.setText("Multi Players! - Yes");
                switchMultiPlayer.setTextColor(Color.parseColor("#4CAF50"));

/*                Log.e("Current 4m display: ", ""+currentPlayer);
                Log.e("Player One: ", "" + player1);
                Log.e("Player two:", "" + player2);*/

                if (lastWinner == null) {
                    if (currentPlayer == 1) {
                        txtMessage.setText(player1 + " Your Turn!");        // display message
                    } else {
                        txtMessage.setText(player2 + " Your Turn!");        // display message
                    }
                } else {                        // last winner message
                    if (newCurrentPlayer == 1) {
                        if (currentPlayer == 1)
                            txtMessage.setText(player1 + " Your Turn!");        // display message
                        else
                            txtMessage.setText(player2 + " Your Turn!");        // display message
                    }
                    if (newCurrentPlayer == 2) {
                        if (currentPlayer == 1) {
                            txtMessage.setText(player1 + " Your Turn!");        // display message
                        } else
                            txtMessage.setText(player2 + " Your Turn!");        // display message
                    }
                }

            } else {
                switchMultiPlayer.setText("Multi Players! - No");
                switchMultiPlayer.setTextColor(Color.parseColor("#E91E63"));
                if (lastWinner == null)
                    txtMessage.setText(player1 + " Your Turn!");        // display message
                else
                    txtMessage.setText(lastWinner + " Your Turn!");        // display message
            }
        }
    }

    public void setImages(int i) {
        if(gameActive && counter < 9) {                     // if game is not finished yet
            if(switchMultiPlayer.isEnabled())
                switchMultiPlayer.setEnabled(false);        // disable the switch


            // this will give a motion and effect to the image
            imageViews[i].setTranslationY(-1000f);

            if(multiPlayer) {       // if two players are plying
                if(currentPlayer == 1) {                                // set zero for player one
                    imageViews[i].setImageResource(R.drawable.yellow_mango);    // set image
                    locationContain[i] = 0;                             // array value 0
                    imageViews[i].setClickable(false);                  // disable imageView
                    numOfMovesP1++;
                    checkWin();                                         // check for winner
                    currentPlayer++;
                    counter++;                                          // increas the counter
                } else {                                                // set x for player two
                    imageViews[i].setImageResource(R.drawable.green_mango);    // set image
                    locationContain[i] = 1;                             // array value 1
                    imageViews[i].setClickable(false);                  // disable imageView
                    numOfMovesP2++;
                    checkWin();                                         // check for winner
                    currentPlayer--;
                    counter++;                                          // increas the counter
                }
            }

            // is single player
            if (!multiPlayer) {
                nextMove(i);
            }
            imageViews[i].animate().translationYBy(1000f).setDuration(200);
        }

    }

    public void nextMove(int i) {
        // this will give a motion effect to the image
        imageViews[i].setTranslationY(-1000f);
        if(gameActive && counter < 9) {                             // if game is not finished yet
            if (currentPlayer == 1) {                               // set zero for player one
                imageViews[i].setImageResource(R.drawable.yellow_mango);    // set image
                locationContain[i] = 0;                             // array value 0
                imageViews[i].setClickable(false);                  // disable imageView
                numOfMovesP1++;
                checkWin();                                         // check for winner
                currentPlayer++;
                counter++;                                          // increas the counter
            }
        }

        if(gameActive && counter < 9) {     // if game is not finished yet
            if (currentPlayer != 1) {       // Computer turn

                isComputer = true;          // computer is playing
                numOfMovesP2++;

                // Logic behind decision making move for computer against human move
                if (locationContain[0] == 0 && locationContain[1] == 0
                        && locationContain[2] == 2) {
                    updateComputeMove(2);
                } else if (locationContain[0] == 0 && locationContain[2] == 0
                        && locationContain[1] == 2) {
                    updateComputeMove(1);
                } else if (locationContain[1] == 0 && locationContain[2] == 0
                        && locationContain[0] == 2) {
                    updateComputeMove(0);
                } else if (locationContain[3] == 0 && locationContain[4] == 0
                        && locationContain[5] == 2) {
                    updateComputeMove(5);
                } else if (locationContain[3] == 0 && locationContain[5] == 0
                        && locationContain[4] == 2) {
                    updateComputeMove(4);
                } else if (locationContain[4] == 0 && locationContain[5] == 0
                        && locationContain[3] == 2) {
                    updateComputeMove(3);
                } else if (locationContain[6] == 0 && locationContain[7] == 0
                        && locationContain[8] == 2) {
                    updateComputeMove(8);
                } else if (locationContain[6] == 0 && locationContain[8] == 0
                        && locationContain[7] == 2) {
                    updateComputeMove(7);
                } else if (locationContain[7] == 0 && locationContain[8] == 0
                        && locationContain[6] == 2) {
                    updateComputeMove(6);
                } else if (locationContain[0] == 0 && locationContain[3] == 0
                        && locationContain[6] == 2) {
                    updateComputeMove(6);
                } else if (locationContain[0] == 0 && locationContain[6] == 0
                        && locationContain[3] == 2) {
                    updateComputeMove(3);
                } else if (locationContain[3] == 0 && locationContain[6] == 0
                        && locationContain[0] == 2) {
                    updateComputeMove(0);
                } else if (locationContain[1] == 0 && locationContain[4] == 0
                        && locationContain[7] == 2) {
                    updateComputeMove(7);
                } else if (locationContain[1] == 0 && locationContain[7] == 0
                        && locationContain[4] == 2) {
                    updateComputeMove(4);
                } else if (locationContain[4] == 0 && locationContain[7] == 0
                        && locationContain[1] == 2) {
                    updateComputeMove(1);
                } else if (locationContain[2] == 0 && locationContain[5] == 0
                        && locationContain[8] == 2) {
                    updateComputeMove(8);
                } else if (locationContain[2] == 0 && locationContain[8] == 0
                        && locationContain[5] == 2) {
                    updateComputeMove(5);
                } else if (locationContain[5] == 0 && locationContain[8] == 0
                        && locationContain[2] == 2) {
                    updateComputeMove(2);
                } else if (locationContain[0] == 0 && locationContain[4] == 0
                        && locationContain[8] == 2) {
                    updateComputeMove(8);
                } else if (locationContain[0] == 0 && locationContain[8] == 0
                        && locationContain[4] == 2) {
                    updateComputeMove(4);
                } else if (locationContain[4] == 0 && locationContain[8] == 0
                        && locationContain[0] == 2) {
                    updateComputeMove(0);
                } else if (locationContain[2] == 0 && locationContain[4] == 0
                        && locationContain[6] == 2) {
                    updateComputeMove(6);
                } else if (locationContain[2] == 0 && locationContain[6] == 0
                        && locationContain[4] == 2) {
                    updateComputeMove(4);
                } else if (locationContain[4] == 0 && locationContain[6] == 0
                        && locationContain[2] == 2) {
                    updateComputeMove(2);
                } else {
                    // any other random move
                    for (int j = 0; j < locationContain.length; j++) {
                        if (locationContain[j] == 2) {
                            updateComputeMove(j);
                            break;
                        }
                    }
                }
                imageViews[i].animate().translationYBy(1000f).setDuration(200);
            }
        }
    }

    public void updateComputeMove(int i) {
        // this will give a motion
        // effect to the image
        imageViews[i].setTranslationY(-1000f);

        imageViews[i].setImageResource(R.drawable.green_mango);
        locationContain[i] = 1;
        imageViews[i].setClickable(false);                  // disable imageView
        checkWin();                                         // check for winner
        currentPlayer--;
        counter++;                                          // increas the counter

        imageViews[i].animate().translationYBy(1000f).setDuration(1000);

        isComputer = false;         // computer's turn has finished
    }


    public void checkWin() {        // check who is the winner
        int pointer = 0;
        displayResult = new Intent(GameActivity.this,ResultsActivity.class);;
        TextView playerMessage = findViewById(R.id.txtPlayerTurn);
        // Check if any player has won
        for (int[] winPosition : winningLocation) {
            if (locationContain[winPosition[0]] == locationContain[winPosition[1]] &&
                    locationContain[winPosition[1]] == locationContain[winPosition[2]] &&
                    locationContain[winPosition[0]] != 2) {
                pointer = 1;
                gameActive = false;     // somebody won

                if (locationContain[winPosition[0]] == 0) {
                       if(currentPlayer == 1)
                           lastWinner = player1;

                   displayResult.putExtra("lastWinner", lastWinner);       // pass lastwinner name
                   displayResult.putExtra("isComputer", isComputer);       // is this a computer
                   displayResult.putExtra("numOfMoves", numOfMovesP1);     // number of moves
                   displayResult.putExtra("multiPlayer", multiPlayer);     // does multiplayer
                   displayResult.putExtra("currentPlayer", currentPlayer);
                   displayResult.putExtra("player1", player1);
                   displayResult.putExtra("player2", player2);


                } else {
                     if(currentPlayer == 2)
                        lastWinner = player2;

                    displayResult.putExtra("lastWinner", lastWinner);       // pass lastwinner name
                    displayResult.putExtra("isComputer", isComputer);       // is this a computer
                    displayResult.putExtra("numOfMoves", numOfMovesP2);     // number of moves
                    displayResult.putExtra("multiPlayer", multiPlayer);     // does multiplayer
                    displayResult.putExtra("currentPlayer", currentPlayer);
                    displayResult.putExtra("player1", player1);
                    displayResult.putExtra("player2", player2);
                }

                playerMessage.setText("Click End Game For Score!");

            }
            Log.e("LastWinner: ", "" + lastWinner);
        }


        // set the status if the match draw
        if (counter == 8 && pointer == 0) {
            gameActive = false;
            
            playerMessage.setText("Match Draw!");
            displayResult.putExtra("matchDraw", true);
            displayResult.putExtra("multiPlayer",multiPlayer);          // does multiplayer
            displayResult.putExtra("player1", player1);
            displayResult.putExtra("player2", player2);

            playerMessage.setText("Click End Game For Score!");
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
                Intent openInfo = new Intent(GameActivity.this, InfoActivity.class);
                startActivity(openInfo);
                break;
        }
        return true;
    }
}
