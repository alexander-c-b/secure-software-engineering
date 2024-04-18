package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Game extends AppCompatActivity {
    private ImageButton backButton;
    private ImageButton gameProfButton;
    private Button gameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        backButton = (ImageButton) findViewById(R.id.backButtonGame);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Game.this, MainActivity.class);
                startActivity(back);
            }
        });

        gameProfButton = (ImageButton) findViewById(R.id.profPicButton);
        gameProfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent game = new Intent(Game.this, Profile.class);
                startActivity(game);
            }
        });



        gameButton = (Button) findViewById(R.id.playGameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playGame = new Intent(Game.this, Play.class);
                startActivity(playGame);
            }

        });
    }

}