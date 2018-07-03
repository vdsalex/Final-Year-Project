package com.example.alex.rhymebot;

import android.app.Application;
import android.content.Context;
import android.renderscript.ScriptGroup;

import com.example.alex.rhymebot.BotPackage.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Euterpe extends Application
{
    @Override
    public void onCreate()
    {
        File modeFile = new File(getFilesDir().getPath() + "/mode_file.txt");
        File selectedTabFile = new File(getFilesDir().getPath() + "/selected_tab.txt");
        File vocabFile = new File(getFilesDir().getPath() + "/vocabulary_i.txt");
        File adjFile = new File(getFilesDir().getPath() + "/adjectives_i.txt");
        File advFile = new File(getFilesDir().getPath() + "/adverbs_i.txt");
        File nnsFile = new File(getFilesDir().getPath() + "/nouns_i.txt");
        File vbsFile = new File(getFilesDir().getPath() + "/verbs_i.txt");

        createFile(modeFile);
        createFile(selectedTabFile);
        createFile(new File(getFilesDir().getPath() + "/conversation_file.txt"));
        createFile(new File(getFilesDir().getPath() + "/eminescu.txt"));
        createFile(new File(getFilesDir().getPath() + "/bacovia.txt"));
        createFile(new File(getFilesDir().getPath() + "/stanescu.txt"));
        createFile(vocabFile);
        populateVocabulary(vocabFile);
        createFile(adjFile);
        populateAdjectives(adjFile);
        createFile(advFile);
        populateAdverbs(advFile);
        createFile(vbsFile);
        populateVerbs(vbsFile);
        createFile(nnsFile);
        populateNouns(nnsFile);

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

    private void populateVocabulary(File vocabFile)
    {
        if(vocabFile.length() <= 1)
        {
            try
            {
                InputStream is = getAssets().open("vocabulary.txt");
                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);

                FileOutputStream fos = openFileOutput("vocabulary_i.txt", Context.MODE_PRIVATE);

                fos.write(buffer);

                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void populateAdjectives(File adjFile)
    {
        if(adjFile.length() <= 1)
        {
            try
            {
                InputStream is = getAssets().open("adjectives.txt");
                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);

                FileOutputStream fos = openFileOutput("adjectives_i.txt", Context.MODE_PRIVATE);

                for(byte b: buffer)
                {
                    fos.write(b);
                }

                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void populateNouns(File nnsFile)
    {
        if(nnsFile.length() <= 1)
        {
            try
            {
                InputStream is = getAssets().open("nouns.txt");
                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);

                FileOutputStream fos = openFileOutput("nouns_i.txt", Context.MODE_PRIVATE);

                fos.write(buffer);

                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void populateVerbs(File vbsFile)
    {
        if(vbsFile.length() <= 1)
        {
            try
            {
                InputStream is = getAssets().open("verbs.txt");
                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);

                FileOutputStream fos = openFileOutput("verbs_i.txt", Context.MODE_PRIVATE);

                fos.write(buffer);

                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void populateAdverbs(File advFile)
    {
        if(advFile.length() <= 1)
        {
            try
            {
                InputStream is = getAssets().open("adverbs.txt");
                int size = is.available();
                byte[] buffer = new byte[size];

                is.read(buffer);

                FileOutputStream fos = openFileOutput("adverbs_i.txt", Context.MODE_PRIVATE);

                fos.write(buffer);

                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
