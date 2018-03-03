package com.example.alex.rhymebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Button arrowButton;
    EditText editText;

    // Array of strings...
    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleList = (ListView) findViewById(R.id.listView1);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView);
        simpleList.setAdapter(arrayAdapter);

        arrowButton = (Button) findViewById(R.id.arrow_button);
        editText = (EditText) findViewById(R.id.editText);

        arrowButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String textFromEditText = editText.getText().toString().trim();

                if(!textFromEditText.equals(""))
                {
                    arrayAdapter.add(textFromEditText);
                    simpleList.setAdapter(arrayAdapter);
                    editText.setText("");
                }
            }
        });
    }

}

