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

        Button eminescuButton = (Button) findViewById(R.id.button_eminescu);
        Button bacoviaButton = (Button) findViewById(R.id.button_bacovia);
        Button stanescuButton = (Button) findViewById(R.id.button_stanescu);

        background = getDrawable(R.drawable.dark_grey_rectangle);
        background_pressed = getDrawable(R.drawable.rectangle_pressed);

        setOnTouchListener(eminescuButton);
        setOnTouchListener(bacoviaButton);
        setOnTouchListener(stanescuButton);
    }

    private void setOnTouchListener(final Button btn)
    {
        btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();

                if(action == MotionEvent.ACTION_DOWN)
                {
                    btn.setTextColor(Color.parseColor("#ff4e4e50"));
                    btn.setBackground(background_pressed);
                    CharSequence buttonText = btn.getText();

                    if(buttonText.equals("Eminescu"))
                    {
                        startActivity(new Intent(ModsActivity.this, ModEminescuActivity.class));
                    }
                    else if(buttonText.equals("Bacovia"))
                    {
                        startActivity(new Intent(ModsActivity.this, ModBacoviaActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(ModsActivity.this, ModStanescuActivity.class));
                    }
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    btn.setTextColor(Color.parseColor("#fffce87d"));
                    btn.setBackground(background);
                }

                return true;
            }
        });
    }
}
