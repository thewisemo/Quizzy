package com.example.android.quizzy;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Declare an int variable to hold the quiz score.
    int userRightAnswersScore;
    int userWrongAnswersScore;
    int allRightScore;
    int allWrongScore;
    // Declare EditText object for text entry Question.
    EditText qOneUserInput;
    // Declare a String object and assign it for the right answer for text entry question.
    String rightAnswerOpt1;
    // Declare a String object to hold the user answer for the text entry question.
    String userAnswer;
    boolean rightEntry;
    // Declare question two CheckBoxes & Declare the boolean variables for them.
    CheckBox red;
    boolean redIsRight;
    CheckBox yellow;
    boolean yellowIsWrong;
    CheckBox blue;
    boolean blueIsRight;
    CheckBox green;
    boolean greenIsRight;

    // Declare questions 3,4,5 Radio buttons & Declare their boolean variables for.
    RadioButton earthRadBtn;
    boolean earthRadIsWrong;
    RadioButton mercuryRadBtn;
    boolean mercuryRadIsRight;
    RadioButton gagarinRadBtn;
    boolean gagarinRadIsRight;
    RadioButton tysonRadBtn;
    boolean tysonRadIsWrong;
    RadioButton greenRadBtn;
    boolean greenRadIsWrong;
    RadioButton blueRadBtn;
    boolean blueRadIsRight;

    // This @Override saves selected int values for screen rotation.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
//        // Save the user score initiative variable
        savedInstanceState.putInt("userRightAnswersScore", userRightAnswersScore);
        savedInstanceState.putInt("userWrongAnswersScore", userWrongAnswersScore);
        savedInstanceState.putInt("QuizRightScore", allRightScore);
        savedInstanceState.putInt("QuizWrongScore", allWrongScore);
        // This super to always be called , so it can save the view hierarchy state "userRightAnswersScore changed value"
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setLogo(R.drawable.en_icon);
        actionBar.setDisplayUseLogoEnabled(true);
    }

    // This method to show the Toast message with the user score when the Show Result button is pressed.
    public void showResult(View view) {
        // Assign the declared EditText object for Q1 to its id inside the activity_main.xml.
        qOneUserInput = findViewById(R.id.q_one_edit_tv);
        // Assign the String object which hold the user answer for the text entry question : To get the user entry toString().
        userAnswer = qOneUserInput.getText().toString();
        rightAnswerOpt1 = getString(R.string.j_watt);
        // Assign the boolean rightEntry variable to true when the answer matches the rightAnswerOpt1 global String object.
        rightEntry = userAnswer.trim().matches(rightAnswerOpt1);
        // Assign the CheckBoxes to their ids inside the activity_main.xml
        red = findViewById(R.id.red_check_box);
        blue = findViewById(R.id.blue_check_box);
        green = findViewById(R.id.green_check_box);
        yellow = findViewById(R.id.yellow_check_box);
        // Assign the boolean variables to true when the right answers CheckBox isChecked()
        redIsRight = red.isChecked();
        blueIsRight = blue.isChecked();
        greenIsRight = green.isChecked();

        yellowIsWrong = yellow.isChecked();
        // Assign the RadioButtons to their ids inside the activity_main.xml
        earthRadBtn = findViewById(R.id.radio_earth);
        mercuryRadBtn = findViewById(R.id.radio_mercury);
        gagarinRadBtn = findViewById(R.id.radio_gagarin);
        tysonRadBtn = findViewById(R.id.radio_tyson);
        greenRadBtn = findViewById(R.id.radio_green);
        blueRadBtn = findViewById(R.id.radio_blue);
        // Assign the boolean variables to true when the answers RadioButtons isChecked()
        mercuryRadIsRight = mercuryRadBtn.isChecked();
        gagarinRadIsRight = gagarinRadBtn.isChecked();
        blueRadIsRight = blueRadBtn.isChecked();
        earthRadIsWrong = earthRadBtn.isChecked();
        tysonRadIsWrong = tysonRadBtn.isChecked();
        greenRadIsWrong = greenRadBtn.isChecked();
        // Assign the int variables for Right & Wrong Answers and assign their values to the return values from calculateScore method.
        allRightScore = calculateRightScore(mercuryRadIsRight, gagarinRadIsRight, blueRadIsRight);
        allWrongScore = calculateWrongScore(earthRadIsWrong, tysonRadIsWrong, greenRadIsWrong, yellowIsWrong);
        // create res object to get resources.
        Resources res = getResources();
        if (TextUtils.isEmpty(userAnswer)) {
            qOneUserInput.setError(res.getString(R.string.error_message));
            // Create the Toast to be shown when the Show Result button is pressed.
            Toast toast = Toast.makeText(this, R.string.check_your_first_answer, Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // If the user text entry answer is wrong then update the wrong answers score with one,
        // else right then update the right answers score with one.
        if (!rightEntry) {
            allWrongScore++;
        } else allRightScore++;
        // if the three right answer for CheckBox question are not checked else checked.
        if (!red.isChecked() && !green.isChecked() && !blue.isChecked()) {
            allWrongScore++;
        } else allRightScore++;
        // Declare a String object for the bravo message with score calculated values.
        String bravoMessage = String.format(res.getString(R.string.bravo_message), allRightScore, allWrongScore);
        // The Dialog to be shown when the Show Result button is pressed.
        AlertDialog.Builder scoreResultDialog = new AlertDialog.Builder(MainActivity.this);
        // Setting Dialog Title.
        scoreResultDialog.setTitle(R.string.title);
        // Setting Dialog Message with the score String value.
        scoreResultDialog.setMessage(bravoMessage);
        // Setting Icon to Dialog.
        scoreResultDialog.setIcon(R.drawable.en_icon);
        // Setting Positive "Ok" Btn.
        scoreResultDialog.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Congratulations dialog toast.
                        Toast.makeText(getApplicationContext(),
                                R.string.thanks, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        // Showing Alert Dialog.
        scoreResultDialog.show();
        // Create the Toast to be shown when the Show Result button is pressed "Reviewer request".
        Toast toast = Toast.makeText(this, bravoMessage, Toast.LENGTH_LONG);
        toast.show();
        clearAnswers();
    }

    // This method to calculate the user score and take the answers @Params all right answers.
    private int calculateRightScore(boolean mercuryRadIsRight, boolean gagarinRadIsRight, boolean blueRadIsRight) {
        boolean[] rightAnswers = new boolean[3];
        rightAnswers[0] = mercuryRadIsRight;
        rightAnswers[1] = gagarinRadIsRight;
        rightAnswers[2] = blueRadIsRight;
        // this loop to check all the right answers in the rightAnswers boolean array
        // then update the userRightAnswersScore global variable with the number of true values.
        for (int i = 0; i < 3; i++) {
            if (rightAnswers[i]) {
                userRightAnswersScore++;
            }
        }
        return userRightAnswersScore;
    }

    // This method to calculate the user score and take the answers @Params rightEntry, redIsRight, blueIsRight, greenIsRight.
    private int calculateWrongScore(boolean earthRadIsWrong, boolean tysonRadIsWrong, boolean greenRadIsWrong, boolean yellowIsWrong) {
        boolean[] wrongAnswers = new boolean[4];
        wrongAnswers[0] = earthRadIsWrong;
        wrongAnswers[1] = tysonRadIsWrong;
        wrongAnswers[2] = greenRadIsWrong;
        wrongAnswers[3] = yellowIsWrong;
        // this loop to check all the wrong answers in the wrongAnswers boolean array
        // then update the userWrongAnswersScore global variable with the number of true values.
        for (int i = 0; i < 4; i++) {
            if (wrongAnswers[i]) {
                userWrongAnswersScore++;
            }
        }
        return userWrongAnswersScore;
    }

    // This method to clear/reset the quiz answers.
    private void clearAnswers() {
        qOneUserInput.getText().clear();
        red.setChecked(false);
        blue.setChecked(false);
        yellow.setChecked(false);
        green.setChecked(false);
        earthRadBtn.setChecked(false);
        mercuryRadBtn.setChecked(false);
        gagarinRadBtn.setChecked(false);
        tysonRadBtn.setChecked(false);
        greenRadBtn.setChecked(false);
        blueRadBtn.setChecked(false);
    }

    // This method to call saved data in onSaveInstanceState.
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // get  userRightAnswersScore saved in onSavedInstanceState key
        userRightAnswersScore = savedInstanceState.getInt("userRightAnswersScore");
        userWrongAnswersScore = savedInstanceState.getInt("userWrongAnswersScore");
        allRightScore = savedInstanceState.getInt("QuizRightScore");
        allWrongScore = savedInstanceState.getInt("QuizWrongScore");
        // Always call the superclass so it can restore the view hierarchy "userRightAnswersScore value"
        super.onRestoreInstanceState(savedInstanceState);
    }
}