package com.example.alex.rhymebot.Activities;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alex.rhymebot.Adapter.ChatMessage;
import com.example.alex.rhymebot.Adapter.MessagesAdapter;

import com.example.alex.rhymebot.Bot;
import com.example.alex.rhymebot.R;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity
{
    private EditText editText;
    private Bot bot;
    private final long DOUBLE_CLICK_INTERVAL = 250;
    private long currentClickTime;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        lastClickTime = 0;

        bot = new Bot();
        ArrayList<String> someMessages = new ArrayList<>();

        final ListView messagesList = (ListView) this.findViewById(R.id.listView);
        final MessagesAdapter messagesAdapter = new MessagesAdapter(this);
        messagesList.setAdapter(messagesAdapter);

        ImageButton sendButton = (ImageButton) findViewById(R.id.imageButton2);
        editText = (EditText) findViewById(R.id.editText2);

        messagesList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messagesList.setAdapter(messagesAdapter);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String editTextMessage = editText.getText().toString().trim();

                if(!editTextMessage.equals(""))
                {
                    messagesAdapter.add(editTextMessage);
                    String botAnswer = bot.answer(editTextMessage);

                    messagesAdapter.add(botAnswer);
                    messagesList.setSelection(messagesAdapter.getCount() - 1);
                    editText.setText("");
                }
            }
        });

        messagesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(position % 2 == 1)
                {
                    currentClickTime = System.currentTimeMillis();

                    if(currentClickTime - lastClickTime <= DOUBLE_CLICK_INTERVAL)
                    {
                        Toast.makeText(getApplicationContext(), "Versurile au fost salvate!", Toast.LENGTH_SHORT).show();
                    }

                    lastClickTime = currentClickTime;
                }
            }
        });
    }
}
