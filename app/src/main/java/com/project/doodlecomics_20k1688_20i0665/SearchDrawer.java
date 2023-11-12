package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchDrawer extends AppCompatActivity {

    TextView home, newComic, search, notifications, challenges, collaboration, templates, remix;
    LinearLayout backHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_drawer);

        home = findViewById(R.id.home);
        newComic = findViewById(R.id.newComic);
        search = findViewById(R.id.search);
        notifications = findViewById(R.id.notifications);
        challenges = findViewById(R.id.challenges);
        collaboration = findViewById(R.id.collaboration);
        templates = findViewById(R.id.templates);
        remix = findViewById(R.id.remix);
        backHome = findViewById(R.id.backhome);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Search.class);
                startActivity(intent);

            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Homepage.class);
                startActivity(intent);
            }
        });

        newComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, CreateNewComic.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Search.class);
                startActivity(intent);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Notifications.class);
                startActivity(intent);
            }
        });

        challenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, ComicChallenge.class);
                startActivity(intent);
            }
        });

        collaboration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Collaboration.class);
                startActivity(intent);
            }
        });

        templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, Templates.class);
                startActivity(intent);
            }
        });

        remix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDrawer.this, RemixComic.class);
                startActivity(intent);
            }
        });

    }
}