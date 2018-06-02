package com.example.alex.rhymebot.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.alex.rhymebot.R;

public class ModsActivity extends AppCompatActivity
{
    Drawable background;
    Drawable background_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mods);

        final Button eminescuButton = (Button) findViewById(R.id.button_eminescu);
        final Button bacoviaButton = (Button) findViewById(R.id.button_bacovia);
        final Button stanescuButton = (Button) findViewById(R.id.button_stanescu);

        background = getDrawable(R.drawable.dark_grey_rectangle);
        background_pressed = getDrawable(R.drawable.rectangle_pressed);

        eminescuButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN)
                {
                    eminescuButton.setTextColor(Color.parseColor("#ff4e4e50"));
                    eminescuButton.setBackground(background_pressed);
                    CharSequence buttonText = eminescuButton.getText();

                    if(buttonText.equals("Eminescu"))
                    {
                        startActivity(new Intent(ModsActivity.this, ModEminescuActivity.class));
                    }
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    eminescuButton.setTextColor(Color.parseColor("#fffce87d"));
                    eminescuButton.setBackground(background);
                }

                return true;
            }
        });

        bacoviaButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN)
                {
                    bacoviaButton.setTextColor(Color.parseColor("#ff4e4e50"));
                    bacoviaButton.setBackground(background_pressed);
                    CharSequence buttonText = bacoviaButton.getText();

                    if(buttonText.equals("Bacovia"))
                    {
                        startActivity(new Intent(ModsActivity.this, ModBacoviaActivity.class));
                    }
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    bacoviaButton.setTextColor(Color.parseColor("#fffce87d"));
                    bacoviaButton.setBackground(background);
                }

                return true;
            }
        });

        stanescuButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN)
                {
                    stanescuButton.setTextColor(Color.parseColor("#ff4e4e50"));
                    stanescuButton.setBackground(background_pressed);
                    CharSequence buttonText = stanescuButton.getText();

                    if(buttonText.equals("Stănescu"))
                    {
                        startActivity(new Intent(ModsActivity.this, ModStanescuActivity.class));
                    }
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    stanescuButton.setTextColor(Color.parseColor("#fffce87d"));
                    stanescuButton.setBackground(background);
                }

                return true;
            }
        });
    }
}
