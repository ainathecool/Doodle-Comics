package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import yuku.ambilwarna.AmbilWarnaDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ComicStrip extends AppCompatActivity {

    private int selectedColor = Color.BLACK; // Default color
    private int colorOrComic = 1; //1 for comic, 2 for color
    private Bitmap canvasBitmap; // Bitmap to store the canvas
    private Canvas canvas; // Canvas to draw on
    private List<ImageView> canvasImageViewList;
    private List<ImageView> undoneActions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_strip);


        // Retrieve details from the intent
//        Intent intent = getIntent();
//        String title = intent.getStringExtra("comicTitle");
//        String genre = intent.getStringExtra("comicGenre");
//        String description = intent.getStringExtra("comicDescription");
//        String comicCoverUri = intent.getStringExtra("comicCoverUri");

        canvasImageViewList = new ArrayList<>();
        undoneActions = new ArrayList<>();

//        TextView undo = findViewById(R.id.undoButton);
//        undo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                undoAction();
//            }
//        });
//
//        TextView redo = findViewById(R.id.redoButton);
//        redo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redoAction();
//            }
//        });
        TextView uploadComicButton = findViewById(R.id.uploadComicButton);
        uploadComicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve details from the intent
                Intent intent = getIntent();
                String title = intent.getStringExtra("comicTitle");
                String genre = intent.getStringExtra("comicGenre");
                String description = intent.getStringExtra("comicDescription");
                String comicCoverUri = intent.getStringExtra("comicCoverUri");

                // Create an instance of the Comics class and set its properties
                Comics comic = new Comics();
                comic.setComicTitle(title);
                comic.setComicDescription(description);
                comic.setComicGenre(genre);
                comic.setComicCover(comicCoverUri);

                // Save the comic instance locally
                saveComicToFile(comic);

                // Get the file path of the saved bitmap
                File imageFile = saveBitmapToFile(getBitmapFromView(findViewById(R.id.canvasContainer)));

                // Create an intent to start the UploadComic activity
                Intent uploadIntent = new Intent(ComicStrip.this, UploadComicEmail.class);

                // Pass comic details as extras
                uploadIntent.putExtra("comicTitle", title);
                uploadIntent.putExtra("comicGenre", genre);
                uploadIntent.putExtra("comicDescription", description);
                uploadIntent.putExtra("comicCoverUri", comicCoverUri);
                uploadIntent.putExtra("comicLikes", 0);
                uploadIntent.putExtra("comicRemixes", 0);

                // Pass the file path of the saved bitmap as an extra
                uploadIntent.putExtra("canvasImagePath", imageFile.getAbsolutePath());

                // Start the UploadComic activity
                startActivity(uploadIntent);
            }
        });

        TextView createComic = findViewById(R.id.createComic);
        createComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComicLocally();
            }
        });

        TextView exportComic = findViewById(R.id.exportButton);
        exportComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the View containing the canvas
                View canvasContainer = findViewById(R.id.canvasContainer);

                // Save the canvas as a Bitmap
                Bitmap canvasBitmap = getBitmapFromView(canvasContainer);

                // Save the Bitmap to a file
                File imageFile = saveBitmapToFile(canvasBitmap);

                // Pass the file path to the ExportComic activity
                Intent intent = new Intent(ComicStrip.this, exportComic.class);
                intent.putExtra("canvasImagePath", imageFile.getAbsolutePath());
                startActivity(intent);
            }
        });

        TextView tool1 = findViewById(R.id.tool1); //tool1 is pen
        tool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorPickerDialog();
            }
        });

        // Set OnClickListener for each comicBubble
            setComicBubbleClickListener(R.id.comicBubble01);
            setComicBubbleClickListener(R.id.comicBubble02);
            setComicBubbleClickListener(R.id.comicBubble03);
            setComicBubbleClickListener(R.id.comicBubble04);
            setComicBubbleClickListener(R.id.comicBubble05);
            setComicBubbleClickListener(R.id.comicBubble06);

    }
    private void setComicBubbleClickListener(int comicBubbleId) {
        TextView comicBubble = findViewById(comicBubbleId);
        colorOrComic = 1;
        comicBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duplicateBackground(comicBubble);
            }
        });
    }


    private void duplicateBackground(TextView comicBubble) {
        // Get the background drawable of the clicked comicBubble
        Drawable background = comicBubble.getBackground();

        // Create a new ImageView for the duplicated background
        ImageView duplicatedBackgroundImageView = new ImageView(this);
        duplicatedBackgroundImageView.setImageDrawable(background);

        // Set a unique identifier for the duplicated ImageView
        duplicatedBackgroundImageView.setId(View.generateViewId());

        // Set the initial scale for the duplicated ImageView
        float initialScale = 0.5f; // You can adjust the scale factor as needed
        duplicatedBackgroundImageView.setScaleX(initialScale);
        duplicatedBackgroundImageView.setScaleY(initialScale);

        // Add the duplicated background to the canvas container (FrameLayout)
        FrameLayout canvasContainer = findViewById(R.id.canvasContainer);
        canvasContainer.addView(duplicatedBackgroundImageView);
        // Set touch listener for the duplicated ImageView
        duplicatedBackgroundImageView.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        // Save the initial touch coordinates
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Move the view based on the touch coordinates
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
//                        if(colorOrComic == 2)
//                        {
//                        // Draw on the canvas
//                        drawOnCanvas(view, event.getRawX() - dX, event.getRawY() - dY);}
                        break;
                    case MotionEvent.ACTION_UP:
                        // Perform a click when the touch is released
                        view.performClick();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
     //   canvasImageViewList.add(copyCanvasState());
    }




    private void drawOnCanvas(View view, float x, float y) {
        ImageView canvasImageView = findViewById(R.id.canvas);
        Drawable drawable = canvasImageView.getBackground();

        // Check if the drawable is not null and is an instance of BitmapDrawable
        if (drawable != null && drawable instanceof BitmapDrawable) {
            // Create a mutable copy of the bitmap
            Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            // Create a canvas using the mutable bitmap
            Canvas canvas = new Canvas(mutableBitmap);

            Paint paint = new Paint();
            paint.setColor(selectedColor);
            paint.setStyle(Paint.Style.FILL);

            float radius = 10;
            canvas.drawCircle(x, y, radius, paint);

            // Set the updated bitmap to the canvas ImageView
            canvasImageView.setImageBitmap(mutableBitmap);
        } else {
            // Handle the case where the drawable is null or not an instance of BitmapDrawable
            // You may want to show a message or log a warning
            Toast.makeText(this, "Cannot draw on the canvas.", Toast.LENGTH_SHORT).show();
        }
    }



    private void showColorPickerDialog() {
        // Initial color for the color picker
        int initialColor = selectedColor;

        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(this, initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // Set the selected color
                selectedColor = color;
                colorOrComic = 2;
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // Do nothing or handle cancel
            }
        });

        colorPickerDialog.show();
    }
//    private void undoAction() {
//        if (canvasImageViewList.size() > 1) {
//            ImageView removedAction = canvasImageViewList.remove(canvasImageViewList.size() - 1);
//            undoneActions.add(removedAction);
//
//            // Update the canvas with the previous state
//            updateCanvasState();
//        } else {
//            Toast.makeText(this, "Nothing to undo", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void redoAction() {
//        if (!undoneActions.isEmpty()) {
//            ImageView redoAction = undoneActions.remove(undoneActions.size() - 1);
//            // Update the canvas with the redo action
//            updateCanvasWithState(redoAction);
//            // Add the redo action to the canvasImageViewList
//            canvasImageViewList.add(copyImageView(redoAction));
//        } else {
//            Toast.makeText(this, "Nothing to redo", Toast.LENGTH_SHORT).show();
//        }
//    }


    // Helper method to convert a view to a Bitmap
    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        File imagesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(imagesDir, "doodle_comic.png");

        try {
            OutputStream os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            return null;
        }
    }

    private void saveComicLocally() {
        // Retrieve details from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("comicTitle");
        String genre = intent.getStringExtra("comicGenre");
        String description = intent.getStringExtra("comicDescription");
        String comicCoverUri = intent.getStringExtra("comicCoverUri");

        // Create an instance of the Comics class and set its properties
        Comics comic = new Comics();
        comic.setComicTitle(title);
        comic.setComicDescription(description);
        comic.setComicGenre(genre);
        comic.setComicCover(comicCoverUri);

        // Save the comic instance locally
        saveComicToFile(comic);
    }

    private void saveComicToFile(Comics comic) {
        // Define a filename for the serialized comic instance
        String filename = "comic_data.ser";

        try {
            // Create a file to save the serialized data
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);


            // Open a FileOutputStream to write the serialized data
            FileOutputStream fos = new FileOutputStream(file);

            // Create an ObjectOutputStream to serialize the comic instance
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write the comic instance to the ObjectOutputStream
            oos.writeObject(comic);

            // Close the streams
            oos.close();
            fos.close();

            // Display a toast indicating successful saving
            Toast.makeText(this, "Comic saved locally", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            Toast.makeText(this, "Failed to save comic", Toast.LENGTH_SHORT).show();
        }
    }

}