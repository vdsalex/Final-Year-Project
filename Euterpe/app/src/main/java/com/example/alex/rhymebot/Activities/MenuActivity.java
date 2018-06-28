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

public class MenuActivity extends AppCompatActivity
{
    static Drawable background;
    static Drawable background_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn1 = (Button) findViewById(R.id.button_rhymebot);
        Button btn2 = (Button) findViewById(R.id.button_mods);
        Button btn3 = (Button) findViewById(R.id.button_saved_verses);

        background = getDrawable(R.drawable.dark_grey_rectangle);
        background_pressed = getDrawable(R.drawable.rectangle_pressed);

        setTouchListener(btn1);
        setTouchListener(btn2);
        setTouchListener(btn3);
    }

    private void setTouchListener(final Button btn)
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

                    if(buttonText.equals("Conversație"))
                    {
                        startActivity(new Intent(MenuActivity.this, ConversationActivity.class));
                    }
                    else if(buttonText.equals("Moduri"))
                    {
                        startActivity(new Intent(MenuActivity.this, ModsActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(MenuActivity.this, SavedVersesActivity.class));
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
