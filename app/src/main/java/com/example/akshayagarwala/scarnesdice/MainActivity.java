package com.example.akshayagarwala.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userTotalScore, userCurrentScore, compTotalScore, compCurrentScore;
    private final int COMPUTER_TURN_DELAY = 1500;
    Random random = new Random();
    Handler computerTurnHandler;
    Runnable computerTurnRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userCurrentScore = 0;
        userTotalScore = 0;
        compCurrentScore = 0;
        compTotalScore = 0;

        computerTurnHandler = new Handler();
        computerTurnRunnable = new Runnable() {

            @Override
            public void run() {
                computerTurn();
            }
        };
    } // onCreate

    public void rollDice(View view) {
        int r = rollTheDice();

        if (r != 1) {
            userCurrentScore += r;
            updateMsg("user");
        } // if not 1
        else if(r == 1) {
            buttonEnabled(false);
            userCurrentScore = 0;
            updateMsg("user1");
            computerTurnHandler.postDelayed(computerTurnRunnable, COMPUTER_TURN_DELAY);
        } // if 1

    } // roll button handler

    public void resetGame(View view) {
        userCurrentScore = 0;
        userTotalScore = 0;
        compCurrentScore = 0;
        compTotalScore = 0;

        updateMsg("user");
        buttonEnabled(true);
    } // reset button handler

    public void endTurn(View view) {
        userTotalScore += userCurrentScore;

        if (isWinner()) {
            return;
        } // end if winner
        userCurrentScore = 0;

        updateMsg("user");
        computerTurn();
    } // hold button handler

    public void computerTurn() {
        buttonEnabled(false);
        int c = rollTheDice();

        if (c != 1) {
            compCurrentScore += c;
            updateMsg("comp");
        }
        else if (c == 1) {
            compCurrentScore = 0;
            updateMsg("Computer rolled 1");
            buttonEnabled(true);
            return;
        }

        if (compCurrentScore >= 20) {
            compTotalScore += compCurrentScore;

            if (isWinner()) {
                return;
            } // end if winner

            compCurrentScore = 0;
            updateMsg("Computer Holds");
            buttonEnabled(true);
        }
        else {
            computerTurnHandler.postDelayed(computerTurnRunnable, COMPUTER_TURN_DELAY);
        }

    } // computerTurn method

    public int rollTheDice() {
        int r = random.nextInt(6) + 1;
        ImageView image = (ImageView) findViewById(R.id.imageView);

        switch (r) {
            case 1:
                image.setImageResource(R.drawable.dice1);
                break;
            case 2:
                image.setImageResource(R.drawable.dice2);
                break;
            case 3:
                image.setImageResource(R.drawable.dice3);
                break;
            case 4:
                image.setImageResource(R.drawable.dice4);
                break;
            case 5:
                image.setImageResource(R.drawable.dice5);
                break;
            case 6:
                image.setImageResource(R.drawable.dice6);
                break;
            default:
                break;
        } // switch

        return r;
    } // rollTheDice method

    public void updateMsg(String message) {
        TextView info = (TextView) findViewById(R.id.textView);

        if (message.equals("user")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " your turn score: " + userCurrentScore);
        }
        else if (message.equals("user1")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " your turn score: " + userCurrentScore + " " + "You Rolled 1");
        }
        else if(message.equals("Computer rolled 1")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " " + message);
        }
        else if(message.equals("Computer Holds")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " " + message);
        }
        else if(message.equals("Computer Wins")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " " + message);
        }
        else if(message.equals("You Win!!")) {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " " + message);
        }
        else {
            info.setText("Your Score: " + userTotalScore + " Computer Score: " + compTotalScore
                    + " computer turn score: " + compCurrentScore);
        }
    } // updateMsg Method

    public void buttonEnabled(boolean bool) {
        Button holdButton = (Button) findViewById(R.id.buttonHold);
        Button rollButton = (Button) findViewById(R.id.buttonRoll);
        holdButton.setEnabled(bool);
        rollButton.setEnabled(bool);
    } // buttonEnabled method

    public boolean isWinner() {
        if (compTotalScore >= 100) {
            updateMsg("Computer Wins");
            buttonEnabled(false);
            return true;
        }
        else if (userTotalScore >= 100) {
            updateMsg("You Win!!");
            buttonEnabled(false);
            return true;
        }
        else return false;
    } // decideWinner method

} // class
