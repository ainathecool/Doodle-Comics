package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadComicEmail extends AppCompatActivity {

    EditText email;
    TextView upload;

    // Firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference comicsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_comic_email);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        comicsDatabase = FirebaseDatabase.getInstance().getReference("comics");

        // Retrieve extras from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("comicTitle");
        String genre = intent.getStringExtra("comicGenre");
        String description = intent.getStringExtra("comicDescription");
        String comicCoverUri = intent.getStringExtra("comicCoverUri");
        String canvasImagePath = intent.getStringExtra("canvasImagePath");
        String likes = intent.getStringExtra("comicLikes");
        String remixes = intent.getStringExtra("comicRemixes");

        email = findViewById(R.id.email);
        upload = findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the authenticated user
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    // Authenticated user's email
                    String artistEmail = currentUser.getPhoneNumber();

                    // User-entered email
                    String enteredEmail = email.getText().toString().trim();

                    // Upload comic details to Firebase Realtime Database
                    uploadComicToDatabase(title, genre, description, comicCoverUri, canvasImagePath, artistEmail, likes, remixes);


                } else {
                    // User not authenticated, handle accordingly
                    Toast.makeText(UploadComicEmail.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadComicToDatabase(String title, String genre, String description,
                                       String comicCoverUri, String canvasImagePath,
                                       String artistEmail, String likes, String remixes) {
        // Generate a unique key for the comic
        String comicId = comicsDatabase.push().getKey();

        // Create a Comic object with the provided details
        Comics comic = new Comics(title, genre, description, comicCoverUri, canvasImagePath, artistEmail, likes, remixes);

        // Upload the comic to the "comics" table in Firebase Realtime Database
        comicsDatabase.child(comicId).setValue(comic);

        // Notify the user that the upload is successful
        Toast.makeText(UploadComicEmail.this, "Comic uploaded successfully", Toast.LENGTH_SHORT).show();
    }
}
