package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateYourAccount extends AppCompatActivity {

    TextView email, num, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_your_account);

        email = findViewById(R.id.signupWithEmail);
        num = findViewById(R.id.signupWithNumber);
        login = findViewById(R.id.loginScreen);


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(CreateYourAccount.this, SignUpWithEmail.class );
                startActivity(intent);
            }
        });

        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(CreateYourAccount.this, SignUpWithNumber.class );
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(CreateYourAccount.this, LoginOptions.class );
                startActivity(intent);
            }
        });
    }
}