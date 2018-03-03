package com.example.alex.rhymebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity
{
    Button btnRhymeBot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnRhymeBot = (Button) findViewById(R.id.button_rhymebot);

        btnRhymeBot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startRhymeBotActivity = new Intent(MainMenu.this, MainActivity.class);
                MainMenu.this.startActivity(startRhymeBotActivity);
            }
        });
    }
}
