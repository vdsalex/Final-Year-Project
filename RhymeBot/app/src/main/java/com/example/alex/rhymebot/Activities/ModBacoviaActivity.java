package com.example.alex.rhymebot.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alex.rhymebot.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ModBacoviaActivity extends AppCompatActivity
{
    Drawable background;
    Drawable background_pressed;
    boolean activateButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_bacovia);

        final Button activateButton = (Button) findViewById(R.id.button_bacovia_activeaza);
        background = getDrawable(R.drawable.gold_rectangle);
        background_pressed = getDrawable(R.drawable.dark_grey_rectangle);

        try
        {
            if(getActiveMod() == '1')
            {
                pressButton(activateButton);
            }
            else
            {
                activateButtonPressed = false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        activateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!activateButtonPressed)
                {
                    activateButton.setTextColor(Color.parseColor("#fce87d")); //gold
                    activateButton.setBackground(background_pressed);
                    activateButton.setText(R.string.activate_button_text_pressed);

                    activateButtonPressed = !activateButtonPressed;

                    try
                    {
                        new PrintWriter(getFilesDir().getPath() + "/mode_file.txt").print("");
                        FileOutputStream fileOutputStream = openFileOutput("mode_file.txt", Context.MODE_PRIVATE);
                        fileOutputStream.write('1');
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                    Toast.makeText(getApplicationContext(), "Modul Bacovia a fost activat!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    activateButton.setTextColor(Color.parseColor("#ff4e4e50")); //dark grey
                    activateButton.setBackground(background);
                    activateButton.setText(R.string.activate_button_text_unpressed);

                    activateButtonPressed = !activateButtonPressed;

                    try
                    {
                        new PrintWriter(getFilesDir().getPath() + "/mode_file.txt").print("");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Modul Bacovia a fost dezactivat!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void pressButton(final Button activateButton) throws IOException
    {
        activateButton.setTextColor(Color.parseColor("#fce87d")); //gold
        activateButton.setBackground(background_pressed);
        activateButton.setText(R.string.activate_button_text_pressed);

        activateButtonPressed = !activateButtonPressed;

        new PrintWriter(getFilesDir().getPath() + "/mode_file.txt").print("");
        FileOutputStream fileOutputStream = openFileOutput("mode_file.txt", Context.MODE_PRIVATE);
        fileOutputStream.write('1');
    }

    private char getActiveMod() throws IOException
    {
        File modeFile = new File(getFilesDir().getPath() + "/mode_file.txt");

        if(modeFile.length() != 0)
        {
            FileInputStream fileInputStream = openFileInput("mode_file.txt");
            char c = (char) fileInputStream.read();

            return c;
        }

        return '3';
    }
}

