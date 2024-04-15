package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Play extends AppCompatActivity {
    private ImageButton playButtonBack;
    private Button answerButton1, answerButton2, answerButton3, answerButton4;
    private TextView questionLabel, correctAnswersLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playButtonBack = (ImageButton) findViewById(R.id.backPlay);
        playButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Play.this, Game.class);
                startActivity(back);
            }
        });

        // Find the buttons and labels by their IDs
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        questionLabel = findViewById(R.id.questionLabel);
        correctAnswersLabel = findViewById(R.id.correctAnswersLabel);

        // Set the initial number of correct answers to 0
        correctAnswersLabel.setText("Correct Answers: 0");
    }
}
