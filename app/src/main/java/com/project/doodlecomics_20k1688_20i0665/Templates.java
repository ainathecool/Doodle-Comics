package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Templates extends AppCompatActivity {

    TextView templates;
    ImageButton myButton, myButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        templates = findViewById(R.id.templateDrawer);
        templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Templates.this, TemplatesDrawer.class);
                startActivity(intent);
            }
        });

        myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Templates.this, ChooseTemplates.class);
                startActivity(intent);
            }
        });

        myButton2 = findViewById(R.id.myButton2);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Templates.this, CreateTemplate.class);
                startActivity(intent);
            }
        });
    }
}
