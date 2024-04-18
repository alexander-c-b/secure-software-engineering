package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Play extends AppCompatActivity {
    private ImageButton playButtonBack;
    private Button answerButton1, answerButton2, answerButton3, answerButton4;
    private TextView questionLabel, correctAnswersLabel;
    private GameRoundControl gameRoundControl;
    private int correctAnswersSoFar = 0; // to keep track of the correct answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialize the UI components
        playButtonBack = (ImageButton) findViewById(R.id.backPlay);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        questionLabel = findViewById(R.id.questionLabel);
        correctAnswersLabel = findViewById(R.id.correctAnswersLabel);

        // Set up the back button
        playButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Simply finish the current activity to go back
            }
        });

        // Initialize GameRoundControl
        initGameRoundControl();

        // Set up answer buttons
        setupAnswerButtons();
    }

    private void initGameRoundControl() {
        JsonEncoder jsonEncoder = new JsonEncoder(this); // Pass the Activity context
        try {
            List<Question> questions = jsonEncoder.loadQuestionData(); // Load the questions using JsonEncoder
            GameContent gameContent = new GameContent(questions);
            gameRoundControl = new GameRoundControl(gameContent);
            displayNextQuestion();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions - could not load game data
            Toast.makeText(this, "Error loading game data.", Toast.LENGTH_LONG).show();
            finish(); // Close the activity if there's an error
        }
    }

    private void setupAnswerButtons() {
        Button[] buttons = {answerButton1, answerButton2, answerButton3, answerButton4};
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String feedback = gameRoundControl.tryAnswer(index);
                    Toast.makeText(Play.this, feedback, Toast.LENGTH_SHORT).show();
                    // Update correct answers label only if answer was correct
                    if (feedback.startsWith("Correct")) {
                        correctAnswersSoFar++;
                        correctAnswersLabel.setText("Correct Answers: " + correctAnswersSoFar);
                    }
                    displayNextQuestion(); // Move on to the next question
                }
            });
        }
    }

    private void displayNextQuestion() {
        Question currentQuestion = gameRoundControl.getNextQuestion();
        if (currentQuestion != null) {
            questionLabel.setText(currentQuestion.questionText);
            answerButton1.setText(currentQuestion.answers.get(0));
            answerButton2.setText(currentQuestion.answers.get(1));
            answerButton3.setText(currentQuestion.answers.get(2));
            answerButton4.setText(currentQuestion.answers.get(3));
        } else {
            // No more questions - end the game or show a summary
            gameRoundControl.endGame();
        }
    }

}