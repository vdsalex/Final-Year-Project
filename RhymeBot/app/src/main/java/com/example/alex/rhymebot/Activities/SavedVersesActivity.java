package com.example.alex.rhymebot.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.rhymebot.R;

import java.util.ArrayList;

public class SavedVersesActivity extends AppCompatActivity
{
    private ArrayList<String> savedVerses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_verses);
    }

    public ArrayList<String> getSavedVerses() { return savedVerses; }
}
