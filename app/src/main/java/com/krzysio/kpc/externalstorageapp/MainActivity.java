package com.krzysio.kpc.externalstorageapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private static Bitmap finalPhoto;
    private final static String albumName = "nazwa albumu";
    private Button b_wrtiable;
    private Button b_save;
    private Bitmap photo;
    private ImageView imageView;
    private Button b_read;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_wrtiable = (Button) findViewById(R.id.B_writable);
        b_save = (Button) findViewById(R.id.B_save);
        b_read = (Button) findViewById(R.id.b_read);
        imageView = (ImageView) findViewById(R.id.imageView);
        photo = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        imageView.setImageBitmap(photo);



        b_wrtiable.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExternalStorageWritable()) {
                    Toast.makeText(getApplicationContext(), "Pamięć dostępna", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Pamięć niedostępna", Toast.LENGTH_LONG).show();
                }
            }

        });

        b_save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvertImage(photo);
            }
        });


        b_read.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                readPicture();
            }
        });


    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else
            return false;
    }

    public static void InvertImage(Bitmap original) {
        finalPhoto = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        int A, R, G, B;
        int pixelColor;
        int height = original.getHeight();
        int width = original.getWidth();


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixelColor = original.getPixel(i, j);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                finalPhoto.setPixel(i, j, Color.argb(A, R, G, B));

            }
        }

        try

        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
            FileOutputStream out = new FileOutputStream(file);
            finalPhoto.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readPicture()
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }


}


