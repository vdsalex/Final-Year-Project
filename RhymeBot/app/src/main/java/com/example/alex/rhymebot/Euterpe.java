package com.example.alex.rhymebot;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Euterpe extends Application
{
    @Override
    public void onCreate()
    {
        File modeFile = new File(getFilesDir().getPath() + "/mode_file.txt");
        File selectedTabFile = new File(getFilesDir().getPath() + "/selected_tab.txt");

        createFile(modeFile);
        createFile(selectedTabFile);
        createFile(new File(getFilesDir().getPath() + "/conversation_file.txt"));
        createFile(new File(getFilesDir().getPath() + "/eminescu.txt"));
        createFile(new File(getFilesDir().getPath() + "/bacovia.txt"));
        createFile(new File(getFilesDir().getPath() + "/stanescu.txt"));

        try
        {
            if(modeFile.length() == 0)
            {
                FileOutputStream fileOutputStream = openFileOutput("mode_file.txt", Context.MODE_PRIVATE);
                fileOutputStream.write('0');
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            if(selectedTabFile.length() == 0)
            {
                FileOutputStream fileOutputStream = openFileOutput("selected_tab.txt", Context.MODE_PRIVATE);
                fileOutputStream.write('0');
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        super.onCreate();
    }

    private void createFile(File file)
    {
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
