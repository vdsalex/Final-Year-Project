package com.example.alex.rhymebot.Activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alex.rhymebot.R;

public class ModStanescuActivity extends AppCompatActivity
{
    Drawable background;
    Drawable background_pressed;
    boolean activateButtonPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_stanescu);

        activateButtonPressed = false;
        background = getDrawable(R.drawable.dark_grey_rectangle);
        background_pressed = getDrawable(R.drawable.gold_rectangle);

        final Button activateButton = (Button) findViewById(R.id.button_stanescu_activeaza);

        activateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!activateButtonPressed)
                {
                    activateButton.setTextColor(Color.parseColor("#ff4e4e50")); //dark grey
                    activateButton.setBackground(background_pressed);
                    activateButton.setText(R.string.activate_button_text_pressed);

                    activateButtonPressed = !activateButtonPressed;
                }
                else
                {
                    activateButton.setTextColor(Color.parseColor("#fce87d")); //gold
                    activateButton.setBackground(background);
                    activateButton.setText(R.string.activate_button_text_unpressed);

                    activateButtonPressed = !activateButtonPressed;
                }

            }
        });
    }
}
