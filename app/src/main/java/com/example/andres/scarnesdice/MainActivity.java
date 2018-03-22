package com.example.andres.scarnesdice;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * DECLARE GLOBAL VARIABLES
     */

    int userScore, userTurnScore;
    int cpuScore, cpuTurnScore;

    TextView userScoreText;
    TextView userTurnScoreText;
    TextView cpuScoreText;
    TextView cpuTurnScoreText;

    ImageView dieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate text views.

        userScoreText = findViewById(R.id.user_score);
        userTurnScoreText = findViewById(R.id.user_turn_score);
        cpuScoreText = findViewById(R.id.cpu_score);
        cpuTurnScoreText = findViewById(R.id.cpu_turn_score);

        // Instantiate the die image view.

        dieImage = findViewById(R.id.die);

        // Set all the scores to and display them.

        reset();

        // Instantiate all necessary buttons

        Button rollButton = findViewById(R.id.roll_button);
        rollButton.setOnClickListener(this);

        Button holdButton = findViewById(R.id.hold_button);
        holdButton.setOnClickListener(this);

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
    }

    /*
     * HANDLE BUTTON CLICKS
     */

    @Override
    public void onClick(View view) {

        // Identify which button is clicked and call the corresponding function.

        switch (view.getId()) {
            case R.id.roll_button:
                int roll = roll();
                if (roll == 1) {
                    userTurnScore = 0;
                    refreshText();
                    cpuTurn();
                    break;
                } else {
                    userTurnScore += roll;
                }
                refreshText();
                break;
            case R.id.hold_button:
                hold();
                break;
            case R.id.reset_button:
                reset();
                break;
        }

    }

    /*
     * CPU GAME LOGIC
     */

    // Handle a single turn by the CPU.

    void cpuTurn() {
        int roll;

        roll = roll();

        while (cpuTurnScore < 20) {
            if (roll == 1) {
                cpuTurnScore = 0;
                refreshText();
                return;
            } else {
                cpuTurnScore += roll;
            }
            refreshText();
        }
        cpuScore += cpuTurnScore;
        cpuTurnScore = 0;

        refreshText();

    }

    /*
     * ACTION HELPER FUNCTIONS
     */

    int roll() {

        // Generate a random integer between 1 and 6.

        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;

        // Set die face displayed according to roll.

        Drawable dieFace;

        switch (roll) {
            case 1:
                dieFace = getResources().getDrawable(R.drawable.dice1);
                break;
            case 2:
                dieFace = getResources().getDrawable(R.drawable.dice2);
                break;
            case 3:
                dieFace =  getResources().getDrawable(R.drawable.dice3);
                break;
            case 4:
                dieFace = getResources().getDrawable(R.drawable.dice4);
                break;
            case 5:
                dieFace = getResources().getDrawable(R.drawable.dice5);
                break;
            case 6:
                dieFace = getResources().getDrawable(R.drawable.dice6);
                break;
            default:
                dieFace = getResources().getDrawable(R.drawable.dice1);
                break;
        }

        dieImage.setImageDrawable(dieFace);

        return roll;
    }

    void hold() {

        // Add the user's turn score to the user's total score.

        userScore += userTurnScore;
        userTurnScore = 0;

        // Refresh the text and begin the CPU's turn.

        refreshText();
        cpuTurn();
    }

    void reset() {

        // Set all values to 0.

        userScore = 0;
        userTurnScore = 0;
        cpuScore = 0;
        cpuTurnScore = 0;

        refreshText();

    }

    @SuppressLint("SetTextI18n")
    void refreshText() {

        // Set text views to contain new values.

        userScoreText.setText(getResources().getString(R.string.your_score) + " " + userScore);
        userTurnScoreText.setText(getResources().getString(R.string.your_turn_score) + " " + userTurnScore);
        cpuScoreText.setText(getResources().getString(R.string.cpu_score) + " " + cpuScore);
        cpuTurnScoreText.setText(getResources().getString(R.string.cpu_turn_score) + " " + cpuTurnScore);
    }

}
