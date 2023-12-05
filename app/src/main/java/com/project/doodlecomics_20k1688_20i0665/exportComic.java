package com.project.doodlecomics_20k1688_20i0665;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.provider.MediaStore;
import android.widget.Toast;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class exportComic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_comic);

        // Retrieve the file path from the intent
        String imagePath = getIntent().getStringExtra("canvasImagePath");

        // Load the image using the file path (you can use an ImageView or other UI components as needed)
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        TextView pngFormat = findViewById(R.id.png_button);
        pngFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the file path from the intent
                String imagePath = getIntent().getStringExtra("canvasImagePath");

                // Load the image using the file path
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                // Save the image to the device's gallery
                try {
                    saveImageToGallery(bitmap, "DoodleComic", "DoodleComicImage");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                // Display a toast to indicate that the image is saved
                Toast.makeText(exportComic.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });

        TextView pdfFormat = findViewById(R.id.pdf_button);
        pdfFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the file path from the intent
                String imagePath = getIntent().getStringExtra("canvasImagePath");

                // Load the image using the file path
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

                // Save the image as a PDF file
                try {
                    saveImageAsPdf(bitmap, "DoodleComic", "DoodleComicPDF");
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(exportComic.this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to save the image to the gallery
    private void saveImageToGallery(Bitmap bitmap, String albumName, String displayName) throws FileNotFoundException {
        String savedImagePath;

        // Create a new directory for the album if it doesn't exist
        File albumDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                albumName
        );

        if (!albumDir.exists() && !albumDir.mkdirs()) {
            // Handle the case where the directory could not be created
            Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the image file
        File imageFile = new File(albumDir, displayName + ".png");

        // Save the bitmap to the file
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            savedImagePath = imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Notify the gallery app of the new image
        MediaStore.Images.Media.insertImage(
                getContentResolver(),
                savedImagePath,
                displayName,
                "Doodle Comic Image"
        );
    }

    // Method to save the image as a PDF file
    private void saveImageAsPdf(Bitmap bitmap, String albumName, String displayName) throws IOException {
        // Create a new directory for the album if it doesn't exist
        File albumDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                albumName
        );

        if (!albumDir.exists() && !albumDir.mkdirs()) {
            // Handle the case where the directory could not be created
            Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the PDF file
        File pdfFile = new File(albumDir, displayName + ".pdf");

        // Create a PdfWriter instance
        try (PdfWriter writer = new PdfWriter(pdfFile)) {
            // Create a PdfDocument instance
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Create a Document instance
            Document document = new Document(pdfDocument);

            // Add the image to the PDF document
            Image image = new Image(ImageDataFactory.create(bitmapToByteArray(bitmap)));
            document.add(image);

            // Close the PDF document
            pdfDocument.close();
            Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to convert a Bitmap to a byte array
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
