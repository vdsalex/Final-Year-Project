package com.example.alex.rhymebot;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity
{
    static Drawable background;
    static Drawable background_pressed;
    Button btn1;
    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

        background = getDrawable(R.drawable.rectangle);
        background_pressed = getDrawable(R.drawable.rectangle_pressed);

        setTouchListener(btn1);
        setTouchListener(btn2);
        setTouchListener(btn3);

        String title ="RhymeBot";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#fffce87d")), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

    }

    private static void setTouchListener(final Button btn)
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
