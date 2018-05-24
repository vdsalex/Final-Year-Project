package com.example.alex.rhymebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ConversationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ArrayAdapter<String> messagesAdapter = new ArrayAdapter<String>(this, R.layout.message_item_layout);
    }
}
