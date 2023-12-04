package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class LoginOptions extends AppCompatActivity {

    TextView email, num, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        email = findViewById(R.id.loginWithEmail);
        num = findViewById(R.id.loginWithNumber);
        signup = findViewById(R.id.signupScreen);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginOptions.this, LoginWithEmail.class );
                startActivity(intent);
            }
        });

        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginOptions.this, LoginWithNumber.class );
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginOptions.this, CreateYourAccount.class );
                startActivity(intent);
            }
        });
    }
}