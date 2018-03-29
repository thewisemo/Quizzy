package com.example.android.quizzy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int userScore;
    // declare 1 EditText q1.
    EditText qOneUserInput;
    String rightAnswerOpt1 = "James Watt";
    String userAnswer;

    // This @Override saves selected int values for screen rotation & passing to Standings class.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the players/teams current scores state
        savedInstanceState.putInt("QuizScore", userScore);
        // This super to always be called , so it can save the view hierarchy state "changed values"
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showResult(View view) {
        // Assign the declared EditText object for q1 to its id inside the activity_main.xml.
        qOneUserInput = findViewById(R.id.q_one_edit_tv);
        userAnswer = qOneUserInput.getText().toString();
        boolean rightEntry = userAnswer.trim().matches(rightAnswerOpt1);
        /*
       Declare question two 4 CheckBoxes & assign them to their ids inside the activity_main.xml.
       if the right answer checkBox is checked so increase the userScore value by one
       */
        CheckBox red = findViewById(R.id.red_check_box);
        boolean redIsRight = red.isChecked();
        CheckBox blue = findViewById(R.id.blue_check_box);
        boolean blueIsRight = blue.isChecked();
        CheckBox green = findViewById(R.id.green_check_box);
        boolean greenIsRight = green.isChecked();
        int score = calculateScore(rightEntry, redIsRight, blueIsRight, greenIsRight);
        String message = "Nice! Your Score Is " + score + " Bravo!";
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public int calculateScore(boolean rightEntry, boolean redIsRight, boolean blueIsRight, boolean greenIsRight) {
        if (redIsRight) {
            userScore++;
        }
        if (blueIsRight) {
            userScore++;
        }
        if (greenIsRight) {
            userScore++;
        }
        if (rightEntry) {
            userScore++;
        }
        return userScore;
    }

    public void onFirstRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean firstRadChecked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_mercury:
                if (firstRadChecked)
                    // Mercury is right
                    userScore++;
                break;
            case R.id.radio_earth:
                if (firstRadChecked)
                    Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onSecondRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean secondRadChecked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_gagarin:
                if (secondRadChecked)
                    // Gagarin is right
                    userScore++;
                break;
            case R.id.radio_tyson:
                if (secondRadChecked)
                    Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onThirdRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean thirdRadChecked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_blue:
                if (thirdRadChecked)
                    // Blue is right
                    userScore++;
                break;
            case R.id.radio_green:
                if (thirdRadChecked)
                    Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // This method to call saved data in onSaveInstanceState.
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy "needed values"
        super.onRestoreInstanceState(savedInstanceState);
        // get player/s scores saved in onSavedInstanceState keys
        userScore = savedInstanceState.getInt("QuizScore");
    }
}
