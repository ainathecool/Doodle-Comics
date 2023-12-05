package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ComicChallenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_challenge);

        ImageButton createChallengeButton = findViewById(R.id.createChallengeButton);
        ImageButton participateInChallengeButton = findViewById(R.id.participateInChallengeButton);
        ImageButton listOfEntriesButton = findViewById(R.id.listOfEntriesButton);
        ImageButton declareWinnerButton = findViewById(R.id.declareWinnerButton);

        createChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent for Create Challenge Page
                // Example: Intent intent = new Intent(NewClassName.this, CreateChallengeActivity.class);
                // startActivity(intent);
            }
        });

        participateInChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent for Participate in Challenge Page
            }
        });

        listOfEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent for List of Entries Page
            }
        });

        declareWinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent for Declare Winner Page
            }
        });
    }
}
