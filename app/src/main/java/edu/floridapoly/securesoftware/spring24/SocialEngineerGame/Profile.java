package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Profile extends AppCompatActivity {
    private TextView playerNameTextView;
    private ListView pastScoresListView;
    private ImageButton backButtonGame;
    private ImageButton gameProfButton; // Declare ImageButton variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        playerNameTextView = findViewById(R.id.playerStats);
        pastScoresListView = findViewById(R.id.pastScoresListView);
        backButtonGame = findViewById(R.id.backButtonGame); // Initialize ImageButton


        loadUserData();

        // Add a click listener to the profile screen layout
        findViewById(R.id.profileLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload past scores when the profile screen is clicked
                reloadPastScores();
            }
        });

        // Set click listener for the ImageButton to navigate to Game activity
        backButtonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(Profile.this, Game.class);
                startActivity(gameIntent);
            }
        });

    }

    private void loadUserData() {
        String username = User.getUsername();
        playerNameTextView.setText("Player: " + username);

        try {
            List<PastScore> pastScores =
              new JsonEncoder().loadPastScores(username, User.getPasswordHash(),
                User.getPasswordHashSalted()
              );
            Collections.reverse(pastScores); // To display most recent at top
            displayPastScores(pastScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPastScores(List<PastScore> pastScores) {
        // Update the adapter with the new list of past scores
        PastScoreAdapter adapter = (PastScoreAdapter) pastScoresListView.getAdapter();
        if (adapter == null) {
            adapter = new PastScoreAdapter(this, pastScores);
            pastScoresListView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(pastScores);
            adapter.notifyDataSetChanged();
        }
    }

    private void reloadPastScores() {
        // Clear the list of past scores and reload them
        pastScoresListView.setAdapter(null); // Clear the adapter
        loadUserData(); // Reload past scores
    }
}
