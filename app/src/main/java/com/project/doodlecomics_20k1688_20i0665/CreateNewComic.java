package com.project.doodlecomics_20k1688_20i0665;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateNewComic extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // To store the selected image URI


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_comic);

        TextView homepage = findViewById(R.id.homepageDrawer);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNewComic.this, NewComicDrawer.class);
                startActivity(intent);
            }
        });

        TextView createStripButton = findViewById(R.id.CreateStrip);

        createStripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gather the entered details
                EditText titleEditText = findViewById(R.id.comicTitle);
                EditText genreEditText = findViewById(R.id.comicGenre);
                EditText descriptionEditText = findViewById(R.id.comicDescription);

                String title = titleEditText.getText().toString();
                String genre = genreEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                // Create an intent to start the ComicStrip activity
                Intent intent = new Intent(CreateNewComic.this, ComicStrip.class);

                // Pass the details to the intent
                intent.putExtra("comicTitle", title);
                intent.putExtra("comicGenre", genre);
                intent.putExtra("comicDescription", description);

                // Pass the image URI to the intent
                if (selectedImageUri != null) {
                    intent.putExtra("comicCoverUri", selectedImageUri.toString());
                }


                // Start the ComicStrip activity
                startActivity(intent);
            }
        });

        // Handle comic cover click to open the image picker
        ImageView comicCoverTextView = findViewById(R.id.comicCover);
        comicCoverTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    // Method to open the image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Display the selected image in the ImageView
            ImageView comicCoverImageView = findViewById(R.id.comicCover);
            comicCoverImageView.setImageURI(selectedImageUri);
        }
    }
}