package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Play extends AppCompatActivity {
    private ImageButton playButtonBack;

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

    }
}