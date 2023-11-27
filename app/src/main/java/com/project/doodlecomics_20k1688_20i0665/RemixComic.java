package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RemixComic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remix_comic);


        TextView homepage = findViewById(R.id.homepageDrawer);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemixComic.this, RemixDrawer.class);
                startActivity(intent);
            }
        });
    }
}